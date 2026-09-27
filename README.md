# Task Manager API (Jira-lite)

A RESTful task management API built with Spring Boot, featuring JWT authentication, role-based access control, and a multi-entity relational data model — inspired by tools like Jira.

## Features

- **User management** — registration and login with hashed passwords (BCrypt)
- **JWT authentication** — stateless, token-based auth with role claims
- **Role-based access control (RBAC)** — endpoint-level restrictions via Spring Security's `@PreAuthorize`
- **Multi-entity relational model** — Users, Projects, Tasks, and Comments with proper foreign key relationships
- **DTOs** — clean API contracts, no sensitive fields (e.g., passwords) ever exposed
- **Global exception handling** — consistent, clean error responses instead of raw stack traces
- **Dockerized PostgreSQL** — easy local setup via Docker Compose

## Tech Stack

- **Language:** Java 17
- **Framework:** Spring Boot (Web, Data JPA, Security)
- **Database:** PostgreSQL (via Docker)
- **Authentication:** JWT (JJWT library)
- **Build tool:** Maven

## Architecture 
Client (Postman/Frontend)
↓
Controller (REST endpoints)
↓
Service (business logic, DTO conversion)
↓
Repository (Spring Data JPA)
↓
PostgreSQL (Docker container)


Authentication flow: Login → credentials verified → JWT issued → token sent on every subsequent request → `JwtAuthFilter` validates and authorizes → protected endpoints respond based on role.

## Entity Relationships
User ──< Project (one user owns many projects)
Project ──< Task (one project has many tasks)
User ──< Task (one user can be assigned many tasks, optional)
Task ──< Comment (one task has many comments)
User ──< Comment (one user authors many comments)


## Getting Started

### Prerequisites
- Java 17
- Docker
- Maven (or use the included `mvnw` wrapper)

### Setup

1. Clone the repo:
```bash
git clone https://github.com/your-username/task-manager-api.git
cd task-manager-api
```

2. Start the PostgreSQL container:
```bash
docker-compose up -d
```

3. Run the application:
```bash
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`.

## API Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|----------------|
| POST | `/api/v1/users` | Register a new user | No |
| POST | `/api/v1/auth/login` | Log in, returns JWT | No |
| GET | `/api/v1/users` | List all users | No |
| GET | `/api/v1/users/{id}` | Get user by ID | Yes |
| GET | `/api/v1/projects` | List all projects | Yes |
| GET | `/api/v1/projects/{id}` | Get project by ID | Yes |
| POST | `/api/v1/projects` | Create a project | Yes |
| DELETE | `/api/v1/projects/{id}` | Delete a project | Yes (ADMIN only) |
| GET | `/api/v1/tasks` | List all tasks | Yes |
| GET | `/api/v1/tasks/{id}` | Get task by ID | Yes |
| POST | `/api/v1/tasks` | Create a task | Yes |
| GET | `/api/v1/comments` | List all comments | Yes |
| GET | `/api/v1/comments/{id}` | Get comment by ID | Yes |
| POST | `/api/v1/comments` | Create a comment | Yes |

## Sample Request

**Login:**
```json
POST /api/v1/auth/login
{
  "username": "test_user",
  "password": "mySecret123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzM4NCJ9..."
}
```

Use this token in the `Authorization` header as `Bearer <token>` for all protected endpoints.

## Notable Design Decisions

- **DTOs over raw entities** — prevents accidental exposure of sensitive fields (like passwords) even through nested relationships
- **BCrypt password hashing** — passwords are never stored or transmitted in plain text
- **Stateless JWT auth** — no server-side session storage, suitable for scaling horizontally
- **Explicit cascading deletes** — deleting a project explicitly removes its dependent tasks and comments in the correct order, respecting foreign key constraints

## Future Improvements

- Ownership-based authorization (e.g., project owners can manage their own projects, not just admins)
- Refresh tokens
- Pagination for list endpoints
- Integration tests

---

Built as a learning project to practice Spring Boot, Spring Security, JWT authentication, and REST API design principles.
