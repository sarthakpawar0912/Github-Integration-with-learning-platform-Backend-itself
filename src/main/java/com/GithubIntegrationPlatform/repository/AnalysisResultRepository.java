package com.GithubIntegrationPlatform.repository;

import com.GithubIntegrationPlatform.entity.AnalysisResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalysisResultRepository extends JpaRepository<AnalysisResult, Long> {
}