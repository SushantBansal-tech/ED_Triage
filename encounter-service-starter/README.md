# Encounter Service Starter

A starter full-stack codebase for a healthcare Encounter Service with a React frontend dashboard and a Java Spring Boot backend.

## Stack
- Frontend: React, TypeScript, Vite, React Router, TanStack Query, MUI
- Backend: Java 21, Spring Boot 3, Spring Web, Spring Data JPA, PostgreSQL, Flyway, Validation

## Structure
- `frontend/` React encounter dashboard
- `backend/` Spring Boot encounter service
- `docs/` API and design notes

## Core modules
- Encounter queue dashboard
- Encounter detail page
- Encounter create form
- Encounter status update workflow
- REST APIs for encounter lifecycle

## Frontend folders
```text
src/
  api/
  app/
  components/
  features/encounters/
  layouts/
  pages/
  routes/
  theme/
  types/
```

## Backend folders
```text
src/main/java/com/acme/encounter/
  config/
  controller/
  dto/
  entity/
  mapper/
  repository/
  service/
  exception/
```

## Run order
1. Start PostgreSQL.
2. Run backend with Spring Boot.
3. Run frontend with Vite.
4. Open the dashboard and test create/list/update encounter flows.
