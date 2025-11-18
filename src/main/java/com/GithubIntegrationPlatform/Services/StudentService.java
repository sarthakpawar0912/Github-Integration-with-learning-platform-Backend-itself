package com.GithubIntegrationPlatform.Services;

import com.GithubIntegrationPlatform.dto.RegisterStudentDto;
import com.GithubIntegrationPlatform.entity.Student;
import com.GithubIntegrationPlatform.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;
    private final GitHubService gitHubService;

    public Student registerStudent(RegisterStudentDto dto) {
        if (studentRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalStateException("Email already taken");
        }
        if (studentRepository.findByGithubUsername(dto.getGithubUsername()).isPresent()) {
            throw new IllegalStateException("GitHub username already registered");
        }

        Student student = new Student();
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setGithubUsername(dto.getGithubUsername());

        student = studentRepository.save(student);

        // Create main repo
        String repoName = "training-" + dto.getGithubUsername() + "-main";
        String repoUrl = gitHubService.createRepository(repoName, true);
        student.setMainRepoUrl(repoUrl);

        gitHubService.addCollaborator(repoName, dto.getGithubUsername());

        return studentRepository.save(student);
    }
}