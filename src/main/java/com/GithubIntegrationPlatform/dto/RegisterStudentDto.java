package com.GithubIntegrationPlatform.dto;
import lombok.Data;

@Data
public class RegisterStudentDto {
    private String name;
    private String email;
    private String githubUsername;
}