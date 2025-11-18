package com.GithubIntegrationPlatform.repository;

import com.GithubIntegrationPlatform.entity.Submission;
import com.GithubIntegrationPlatform.entity.TaskRepo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    boolean existsByTaskRepoAndCommitSha(TaskRepo taskRepo, String commitSha);
}