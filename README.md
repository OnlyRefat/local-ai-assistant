# Spring Boot RAG Application

A Retrieval-Augmented Generation (RAG) app using Spring AI, Ollama, and PgVector.

## Tech Stack
- Spring Boot 4.0.5
- Spring AI 2.0.0-M4
- Java 26
- Docker
- Ollama (gemma2:2b for chat, nomic-embed-text for embeddings)
- PostgreSQL + PgVector

## Setup
1. Install Java 26
2. Install ollama, check in your terminal by the following commands
   ```bash
   ollama pull gemma2:2b
   ```
3. Set environment variables:
   ```
   > touch .env
   > nano .env
   DB_USERNAME=your_db_username
   DB_PASSWORD=your_db_password
   ```
4. Run: `./mvnw spring-boot:run`

## Usage
POST `/api/chat` with a question about Spring Boot.