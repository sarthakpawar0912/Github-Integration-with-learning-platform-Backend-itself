package com.GithubIntegrationPlatform.dto;
import lombok.Data;

@Data
public class CreateTaskDto {
    private String title;
    private String blockName;
    private String description;
}