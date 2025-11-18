package com.GithubIntegrationPlatform.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "task_repos")
public class TaskRepo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Task task;

    @ManyToOne
    private Student student;

    private String repoName; // e.g. "yourusername/training-sarthakpawar0912-dsa-arrays"
    private String repoUrl;

    private LocalDateTime createdAt = LocalDateTime.now();
}