package com.GithubIntegrationPlatform.repository;

import com.GithubIntegrationPlatform.entity.TaskRepo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepoRepository extends JpaRepository<TaskRepo, Long> {
    Optional<TaskRepo> findByRepoName(String repoName);
}