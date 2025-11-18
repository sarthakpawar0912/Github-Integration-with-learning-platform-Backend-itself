package com.GithubIntegrationPlatform.controller;

import com.GithubIntegrationPlatform.Services.TaskService;
import com.GithubIntegrationPlatform.dto.CreateTaskDto;
import com.GithubIntegrationPlatform.entity.Task;
import com.GithubIntegrationPlatform.entity.TaskRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> create(@RequestBody CreateTaskDto dto) {
        return ResponseEntity.status(201).body(taskService.createTask(dto));
    }

    @PostMapping("/{id}/create-repos")
    public ResponseEntity<List<TaskRepo>> createRepos(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.createReposForTask(id));
    }

    @GetMapping
    public List<Task> list() {
        return taskService.taskRepository.findAll();
    }
}