package com.GithubIntegrationPlatform.controller;

import com.GithubIntegrationPlatform.entity.TaskRepo;
import com.GithubIntegrationPlatform.repository.SubmissionRepository;
import com.GithubIntegrationPlatform.repository.TaskRepoRepository;
import com.GithubIntegrationPlatform.Services.WebhookService;

import com.fasterxml.jackson.databind.*;
import jakarta.servlet.http.HttpServletRequest;

 // ← THIS LINE WAS MISSING
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class WebhookController {

    private final TaskRepoRepository taskRepoRepository;
    private final SubmissionRepository submissionRepository;
    private final WebhookService webhookService;
    @Value("${github.webhook.secret}")
    private String webhookSecret;

    private final ObjectMapper mapper = new ObjectMapper();

    @PostMapping("/webhook")
    public ResponseEntity<String> webhook(HttpServletRequest request, @RequestBody String payload) throws Exception {
        String event = request.getHeader("X-GitHub-Event");
        String signature = request.getHeader("X-Hub-Signature-256");
        String delivery = request.getHeader("X-GitHub-Delivery");

        if ("ping".equals(event)) return ResponseEntity.ok("pong");

        if (signature == null || !isValidSignature(payload, signature)) {
            return ResponseEntity.status(401).body("Invalid signature");
        }

        JsonNode json = mapper.readTree(payload);

        if ("push".equals(event)) {
            String repoFullName = json.get("repository").get("full_name").asText();
            String ref = json.get("ref").asText();

            if (!"refs/heads/main".equals(ref)) {
                return ResponseEntity.ok("Not main");
            }

            String commitSha = json.get("after").asText();

            TaskRepo taskRepo = taskRepoRepository.findByRepoName(repoFullName)
                    .orElse(null);

            if (taskRepo == null) return ResponseEntity.ok("No task repo");

            if (submissionRepository.existsByTaskRepoAndCommitSha(taskRepo, commitSha)) {
                return ResponseEntity.ok("Already processed");
            }

            webhookService.processPush(taskRepo, commitSha);
        }

        return ResponseEntity.ok("OK");
    }

    private boolean isValidSignature(String payload, String signature) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec keySpec = new SecretKeySpec(webhookSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(keySpec);
        byte[] hmac = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));

        StringBuilder calculated = new StringBuilder("sha256=");
        for (byte b : hmac) {
            calculated.append(String.format("%02x", b & 0xff));
        }

        return calculated.toString().equals(signature);
    }
}