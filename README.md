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

| Feature                                    | Implemented | Interview Gold                     |
|--------------------------------------------|-------------|------------------------------------|
| Auto-create private GitHub repos per student | Yes         | Like GitHub Classroom              |
| One-click assign task to 100+ students     | Yes         | Full automation                    |
| Real-time submission via GitHub push       | Yes         | Webhook mastery                    |
| Secure webhook with HMAC verification      | Yes         | Production security                |
| Idempotency & duplicate prevention         | Yes         | Reliability                        |
| Clean repo naming with `slugify()`         | Yes         | URL-safe names                     |
| Student gets push access automatically    | Yes         | Real dev workflow                  |
| Ready for OpenAI, JUnit, plagiarism check  | Yes         | Future-proof                       |

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
```
    H[Student → git push] --> I[GitHub sends webhook]
    I --> J[Your backend saves submission + runs analysis]
    J --> K[Teacher sees result instantly]
