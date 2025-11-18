package com.GithubIntegrationPlatform.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String blockName;
    private String description;

    private LocalDateTime createdAt = LocalDateTime.now();
}