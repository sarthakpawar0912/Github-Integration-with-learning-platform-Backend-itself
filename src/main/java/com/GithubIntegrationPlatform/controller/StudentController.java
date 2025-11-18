package com.GithubIntegrationPlatform.controller;

import com.GithubIntegrationPlatform.Services.StudentService;
import com.GithubIntegrationPlatform.dto.RegisterStudentDto;
import com.GithubIntegrationPlatform.entity.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/register")
    public ResponseEntity<Student> register(@RequestBody RegisterStudentDto dto) {
        Student student = studentService.registerStudent(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(student);
    }
}