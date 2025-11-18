package com.GithubIntegrationPlatform.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "analysis_results")
public class AnalysisResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "submission_id")
    private Submission submission;

    private Integer unitTestsPassed;
    private Double lintScore;
    private Double aiGeneratedProbability;
    private Double plagiarismScore;
    private String suggestions;

    private LocalDateTime createdAt = LocalDateTime.now();
}