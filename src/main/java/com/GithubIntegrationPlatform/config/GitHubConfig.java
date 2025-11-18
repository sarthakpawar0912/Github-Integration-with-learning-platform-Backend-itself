package com.GithubIntegrationPlatform.config;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GitHubConfig {

    @Getter
    @Value("${github.api.base}")
    private String apiBase;

    @Getter
    @Value("${github.username}")
    private String githubUsername;

    @Getter
    @Value("${github.token}")
    private String token;

    public String getGithubToken() { return token; }
}