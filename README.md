# Banking App

A Spring Boot banking application with JWT-based authentication.

## Features

- User registration and login with JWT authentication
- PostgreSQL database with Flyway migrations
- Spring Security integration
- RESTful API endpoints

## Tech Stack

- Java 17
- Spring Boot 3.4.5
- Spring Security
- Spring Data JPA
- PostgreSQL
- Flyway
- JWT (jsonwebtoken)
- Lombok

## Getting Started

### Prerequisites
- Java 17
- PostgreSQL database

### Running the Application

1. Download dependencies:
   ```bash
   ./mvnw clean compile
   ```

2. Start the application:
   ```bash
   ./mvnw spring-boot:run
   ```

## API Endpoints

### Authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login

See `rest-api.md` for detailed API documentation.

## Database

The application uses PostgreSQL with Flyway for database migrations. Migration files are located in `src/main/resources/db/migration/`.
