package com.GithubIntegrationPlatform.Services;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Service
public class GitHubService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String token;
    private final String owner;
    private final String webhookUrl;
    private final String webhookSecret;
    private final String apiBase = "https://api.github.com";

    public GitHubService(@Value("${github.token}") String token,
                         @Value("${github.owner}") String owner,
                         @Value("${github.webhook.url}") String webhookUrl,
                         @Value("${github.webhook.secret}") String webhookSecret) {
        this.token = token;
        this.owner = owner;
        this.webhookUrl = webhookUrl;
        this.webhookSecret = webhookSecret;

        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().set("Authorization", "token " + token);
            request.getHeaders().set("Accept", "application/vnd.github.v3+json");
            return execution.execute(request, body);
        });
    }

    public String createRepository(String name, boolean isPrivate) {
        String url = apiBase + "/user/repos";

        Map<String, Object> body = new HashMap<>();
        body.put("name", name);
        body.put("private", isPrivate);
        body.put("auto_init", true);
        body.put("description", "Training platform repo");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            return (String) response.getBody().get("html_url");
        }
        throw new RuntimeException("Failed to create repo: " + response.getStatusCode() + " " + response.getBody());
    }

    public void addCollaborator(String repoName, String username) {
        if (username.equalsIgnoreCase(owner)) return;

        String url = apiBase + "/repos/" + owner + "/" + repoName + "/collaborators/" + username;

        Map<String, String> bodyMap = Map.of("permission", "push");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(bodyMap, headers);

        restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
        // Note: GitHub sends invitation email — student must accept it once
    }

    public void createWebhook(String repoName) {
        String url = apiBase + "/repos/" + owner + "/" + repoName + "/hooks";

        Map<String, Object> config = new HashMap<>();
        config.put("url", webhookUrl);
        config.put("content_type", "json");
        config.put("secret", webhookSecret);

        Map<String, Object> body = new HashMap<>();
        body.put("name", "web");
        body.put("active", true);
        body.put("events", List.of("push"));
        body.put("config", config);

        HttpEntity<Map> entity = new HttpEntity<>(body);

        restTemplate.postForEntity(url, entity, Map.class);
    }

    private String slugify(String s) {
        return s == null ? "" : s.trim().toLowerCase()
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("-+$", "").replaceAll("^-+", "");
    }
}