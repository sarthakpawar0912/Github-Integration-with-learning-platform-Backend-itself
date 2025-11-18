package com.GithubIntegrationPlatform.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String githubUsername;

    private String mainRepoUrl;

    private LocalDateTime createdAt = LocalDateTime.now();
}