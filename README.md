# GitHub-Backed Automated Coding Training Platform 
**A real-time, fully automated coding assignment platform where students submit code by simply pushing to GitHub — no ZIP uploads, no manual checking.**

**Live Demo**: Use your ngrok URL (e.g., `https://abc123.ngrok.io`)  
**Tech Stack**: Java 17 • Spring Boot 3 • MySQL • GitHub API • Webhooks • JPA • Lombok  
**Status**: 100% Functional • Production-Ready Architecture

![Real-time Submissions](https://img.shields.io/badge/Real--time%20Submissions-Webhooks-brightgreen) 
![Private Repos](https://img.shields.io/badge/Private%20Repos-Yes-blue) 
![Auto Webhooks](https://img.shields.io/badge/Auto%20Webhooks-Yes-orange)
![No File Upload](https://img.shields.io/badge/No%20File%20Upload-Real%20Git-blueviolet)

---

### Features That Make This Project Stand Out

| Feature                                    | Implemented | Interview Gold |
|--------------------------------------------|-------------|----------------|
| Auto-create private GitHub repos per student | Yes         | Like GitHub Classroom |
| One-click assign task to 100+ students     | Yes         | Full automation |
| Real-time submission via GitHub push       | Yes         | Webhook mastery |
| Secure webhook with HMAC verification      | Yes         | Production security |
| Idempotency & duplicate prevention         | Yes         | Reliability |
| Clean repo naming with `slugify()`         | Yes         | URL-safe names |
| Student gets push access automatically    | Yes         | Real dev workflow |
| Ready for OpenAI, JUnit, plagiarism check  | Yes         | Future-proof |

---

### How It Works (The Magic Flow)

```mermaid
flowchart TD
    A[Teacher creates task] --> B[Call /create-repos]
    B --> C{For each student}
    C --> D[Create private repo<br>training-username-block-task]
    C --> E[Add student as collaborator<br>(push permission)]
    C --> F[Create webhook → your server]
    C --> G[Save link in DB]
    H[Student → git push] --> I[GitHub sends webhook]
    I --> J[Your backend saves submission + runs analysis]
    J --> K[Teacher sees result instantly]

```
#Complete API Guide (Copy-Paste Ready for Postman)
1. Register a Student
POST {{base_url}}/api/auth/register
JSON{
  "name": "Rohan Sharma",
  "email": "rohan@example.com",
  "githubUsername": "rohan-sharma-2025"
}
Response:
JSON{
  "id": 2,
  "mainRepoUrl": "https://github.com/sarthakpawar0912/training-rohan-sharma-2025-main"
}
2. Create a Task
POST {{base_url}}/api/task
JSON{
  "title": "Reverse a String",
  "blockName": "String Manipulation",
  "description": "Reverse string without built-in functions"
}
Response:
JSON{
  "id": 2,
  "title": "Reverse a String"
}
3. Assign Task to All Students (The Magic Button!)
POST {{base_url}}/api/task/2/create-repos ← (use task ID)
Body: {} (empty)
Response (one repo per student):
JSON[
  {
    "repoUrl": "https://github.com/sarthakpawar0912/training-rohan-sharma-2025-string-manipulation-reverse-a-string",
    "repoName": "sarthakpawar0912/training-rohan-sharma-2025-string-manipulation-reverse-a-string"
  }
]
Student accepts invite → clones → pushes → submission captured instantly!

Key Code Explained — Interview-Ready Answers
createReposForTask() — One Click = 100 Repos
Interview Answer:
"This method is the core automation. One API call creates private repos, adds collaborators, sets up webhooks — for every student. It's full infrastructure provisioning, just like GitHub Classroom."
addCollaborator() — Real Access Control
Interview Answer:
"I use GitHub Collaborators API with 'push' permission — students can submit code but not delete the repo. Secure and professional."
slugify() — No More Invalid Names
Interview Answer:
"Converts any title into valid GitHub repo name. Handles spaces, special chars, case — prevents 422 errors."
Webhook Security
Interview Answer:
"I verify every webhook with HMAC-SHA256 and check commit SHA for idempotency — production-grade security."
WebhookService.processPush()
Interview Answer:
"Transactional method that saves submission with 'PROCESSING' → runs analysis → marks 'COMPLETED'. Ready for OpenAI and unit tests."

Setup in 2 Minutes

git clone your-repo
Create DB: platformintegration
Add application.properties with your token + ngrok URL
mvn spring-boot:run
ngrok http 8080
Done!


Future Enhancements (Already Designed)

Run JUnit tests on push
OpenAI code review
Plagiarism detection
Teacher dashboard
Leaderboard
