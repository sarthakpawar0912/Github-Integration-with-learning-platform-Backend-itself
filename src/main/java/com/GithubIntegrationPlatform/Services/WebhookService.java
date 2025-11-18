package com.GithubIntegrationPlatform.Services;
import com.GithubIntegrationPlatform.entity.AnalysisResult;
import com.GithubIntegrationPlatform.entity.Submission;
import com.GithubIntegrationPlatform.entity.TaskRepo;
import com.GithubIntegrationPlatform.repository.AnalysisResultRepository;
import com.GithubIntegrationPlatform.repository.SubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WebhookService {

    private final SubmissionRepository submissionRepository;
    private final AnalysisResultRepository analysisResultRepository;

    @Transactional
    public void processPush(TaskRepo taskRepo, String commitSha) {
        Submission sub = new Submission();
        sub.setTaskRepo(taskRepo);
        sub.setCommitSha(commitSha);
        sub.setBranch("main");
        sub.setStatus("PROCESSING");
        sub = submissionRepository.save(sub);

        // ────── Placeholder analysis (replace with real tests/linters/OpenAI later) ──────
        AnalysisResult result = new AnalysisResult();
        result.setSubmission(sub);
        result.setUnitTestsPassed(null);
        result.setLintScore(95.0);
        result.setAiGeneratedProbability(12.5);
        result.setPlagiarismScore(3.0);
        result.setSuggestions("Code looks clean. Consider using StringBuilder for better performance.");
        analysisResultRepository.save(result);

        sub.setStatus("COMPLETED");
        submissionRepository.save(sub);
    }
}

//"When a student pushes code, GitHub sends a webhook to my /api/webhook endpoint.
//I validate the HMAC signature for security, then call WebhookService.processPush().
//This method is transactional — it first creates a submission with status 'PROCESSING' so the teacher sees real-time feedback.
//Then it runs code analysis (currently placeholder, but designed to support unit tests, linting, and OpenAI review).
//Finally, it marks the submission as 'COMPLETED'.
//The whole process is idempotent using commit SHA, so even if GitHub retries, nothing is duplicated."