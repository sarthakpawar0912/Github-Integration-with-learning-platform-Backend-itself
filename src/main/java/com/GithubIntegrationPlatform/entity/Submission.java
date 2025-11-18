package com.GithubIntegrationPlatform.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "submissions")
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private TaskRepo taskRepo;

    private String commitSha;
    private String branch;
    private String status = "RECEIVED"; // RECEIVED, PROCESSING, COMPLETED, ERROR

    private LocalDateTime pushedAt = LocalDateTime.now();
}