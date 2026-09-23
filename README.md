# Repora

Repora is a full-stack application for connecting to GitHub repositories, indexing codebases, and asking grounded questions about them using retrieval-augmented generation (RAG) and source citations.

It combines:
- a Next.js frontend for the user experience
- a Spring Boot backend for authentication, indexing, and AI-powered retrieval
- a PostgreSQL + pgvector database for storing embeddings and semantic search
- Docker support for local Postgres setup

## Overview

The product experience centers around:
- signing in with GitHub
- connecting to public or private repositories
- indexing repository content into a vector database
- asking questions about the codebase and receiving grounded answers with citations

The repository is structured as a monorepo with separate frontend and backend services:

- `client/` — Next.js application
- `demo/` — Spring Boot Java application
- `docker/` — Docker-related assets
- `docker-compose.yml` — local Postgres service definition

## Features

- GitHub authentication flow
- Repository-based code indexing
- Vector search with Postgres + pgvector
- AI-assisted Q&A over a codebase
- Citation-backed answers for traceability
- Modern UI built with Next.js and React
- Dockerized local database bootstrapping

## Tech Stack

### Frontend
- Next.js
- React
- TypeScript
- Tailwind CSS
- shadcn-style component patterns

### Backend
- Java 21
- Spring Boot 4
- Spring AI
- Spring Security
- JPA / PostgreSQL integration

### Data Layer
- PostgreSQL
- pgvector extension

## Repository Structure

```text
devPilot/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   └── test/
│   ├── pom.xml
│   ├── mvnw
│   └── mvnw.cmd
├── client/
│   ├── app/
│   ├── components/
│   ├── hooks/
│   ├── lib/
│   ├── public/
│   ├── package.json
│   ├── next.config.ts
│   └── README.md
├── docker/
├── docker-compose.yml
├── .gitignore
└── README.md

Getting Started
Prerequisites
Before running the project locally, make sure you have:

Node.js 20+
npm
Java 21
Maven or the included Maven wrapper
Docker and Docker Compose
1) Start the database
From the repository root:

docker compose up -d
This starts PostgreSQL with the pgvector extension enabled.

2) Run the backend
cd backend
./mvnw spring-boot:run
The backend is a Spring Boot service and will expose the API on the default Spring Boot port (typically http://localhost:8080 unless configured otherwise).

3) Run the frontend
cd client
npm install
npm run dev
Then open:


http://localhost:3000
Development Notes
The client app is built with the App Router pattern in client/app/.
The backend uses a standard Spring Boot package layout under backend/src/main/java/devPilot/backend/.
The project is designed around repository indexing and AI-powered code chat workflows.
Environment and Configuration
The repository includes support for:

GitHub OAuth or GitHub login flow
AI model integration through Spring AI
PostgreSQL vector storage
You will need to configure the required credentials and environment settings for GitHub OAuth and your AI provider before using the application end-to-end.

License
This repository does not currently declare a license in the project metadata, so no explicit license is set at the repository level.

Contributing
Contributions are welcome. If you are working on this project locally, the expected flow is:

Create a feature branch
Make focused changes
Validate frontend/backend behavior
Open a pull request with a clear summary of the change

Summary
Repora is a code-aware assistant for GitHub repositories, enabling users to connect their repos, index their code, and ask questions with AI-powered retrieval and source-aware answers. The repository demonstrates a modern full-stack application architecture with a Java backend, a Next.js frontend, and a PostgreSQL vector store.