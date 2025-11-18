package com.GithubIntegrationPlatform.repository;
import com.GithubIntegrationPlatform.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}