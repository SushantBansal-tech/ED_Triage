# AI-Assisted Emergency Care Platform

A polished, interview-ready project README for a healthcare workflow platform that manages patient intake, appointments, queueing, triage, doctor consultation, prescriptions, and future AI-assisted decision support. This project is designed to showcase backend engineering, system design, security, data modeling, and product thinking in one coherent story.

## Overview

This platform models a modern hospital or clinic workflow from patient onboarding to consultation and follow-up. The core system focuses on reliable CRUD, role-based access control, queue-aware operations, and auditable medical workflows before layering in AI/ML features.

The strongest interview angle is that the product is not presented as “AI first.” Instead, it is presented as a robust clinical workflow system with a future-ready AI layer for triage assistance, queue prioritization, and symptom understanding.

## Problem Statement

Hospitals and emergency or outpatient care centers often face fragmented workflows across registration, triage, consultation, prescriptions, and patient tracking. Inefficient front-desk flow and queueing can increase waiting time, hurt patient experience, and reduce operational efficiency.

This project solves that by building a centralized care platform where different users operate through clearly defined roles and workflows:
- Patients register and book appointments.
- Reception staff manage intake and queue movement.
- Doctors review patient context, diagnose, and prescribe.
- Admins govern users, departments, schedules, and system policies.
- AI services later assist with triage scoring, symptom extraction, and prioritization suggestions.

## Why This Project Stands Out

This project is strong for interviews because it demonstrates:
- Real-world domain modeling.
- Secure authorization design.
- Relational schema thinking.
- High-level and low-level architecture clarity.
- A realistic AI/ML integration roadmap instead of vague “AI-powered” claims.

## Core Features

### Current application scope
- User registration and login.
- Role-based access control for patient, doctor, receptionist, and admin.
- Patient profile management.
- Doctor profile and availability management.
- Appointment booking and cancellation.
- Queue and visit lifecycle tracking.
- Consultation notes and prescription creation.
- Audit-friendly status transitions.

### Future AI/ML scope
- Symptom text parsing with NLP.
- Triage severity prediction from vitals and complaints.
- Queue prioritization recommendations.
- Risk flags for escalation.
- Explainable output showing why the model recommended a priority level.

## Users and Roles

| Role | Main responsibilities |
|---|---|
| Patient | Register, log in, manage profile, book appointments, view history |
| Receptionist | Verify patient, create walk-in visits, manage queue, update check-in status |
| Doctor | View assigned queue, access patient context, add diagnosis, create prescription |
| Admin | Manage users, departments, schedules, policies, analytics |

This role separation helps explain both authorization design and bounded responsibilities during interviews.

## High-Level Design

At a high level, the system can be described as a modular monolith first, with clean service boundaries that can later evolve into microservices if scale requires it. That is usually a stronger early-stage architecture choice than starting with many distributed services.

### Major modules
- **Auth module**: registration, login, token handling, password reset, RBAC.
- **User module**: patient, doctor, receptionist, admin profiles.
- **Appointment module**: slot booking, cancellation, reschedule.
- **Visit/Queue module**: walk-ins, check-in, waiting queue, consultation state.
- **Clinical module**: triage data, diagnosis, notes, prescriptions.
- **AI module**: NLP parsing, scoring, prioritization recommendations.
- **Audit/Notification module**: logs, reminders, queue updates.

### HLD diagram

```text
+--------------------+      +---------------------+
|   Web / Mobile UI  | ---> |   API Gateway /     |
|  Patient/Staff App |      |   Backend App       |
+--------------------+      +----------+----------+
                                        |
        +-------------------------------+-------------------------------+
        |               |                  |              |             |
        v               v                  v              v             v
+---------------+ +-------------+ +---------------+ +------------+ +--------------+
| Auth Service  | | User Service| | Appointment   | | Queue/Visit| | Clinical     |
| RBAC, JWT     | | Profiles    | | Scheduling    | | Workflow   | | Notes/Rx     |
+-------+-------+ +------+------+ +-------+-------+ +------+-----+ +------+-------+
        |                |                |                |              |
        +----------------+----------------+----------------+--------------+
                                         |
                                         v
                               +-------------------+
                               | PostgreSQL DB     |
                               | Core transactional|
                               +-------------------+
                                         |
                                         v
                               +-------------------+
                               | AI/ML Service     |
                               | NLP + Triage      |
                               +-------------------+
```

## Low-Level Design

The low-level flow focuses on a patient journey and the internal system actions triggered at each step.

### Booking and visit flow
1. Patient signs up or logs in.
2. Patient books an appointment or reception creates a walk-in visit.
3. System validates doctor availability and visit type.
4. Visit record is created with status such as `BOOKED`, `CHECKED_IN`, `IN_QUEUE`, `IN_CONSULTATION`, `COMPLETED`, or `CANCELLED`.
5. Reception or triage desk records chief complaint, vitals, and basic intake data.
6. Doctor opens the active visit, reviews patient history, adds diagnosis and treatment.
7. Prescription and visit summary are stored.
8. Audit logs record status transitions and sensitive updates.

### LLD sequence

```text
Patient/Reception -> Auth Module -> User Module -> Appointment Module -> Visit Module -> Clinical Module -> Database

Detailed flow:
1. Authenticate user
2. Validate role permissions
3. Create or fetch patient record
4. Create appointment / walk-in visit
5. Update visit state on check-in
6. Capture triage and complaint details
7. Push into queue based on visit state
8. Open doctor consultation context
9. Persist notes, diagnosis, prescription
10. Mark visit complete and save audit trail
```

## Suggested Backend Folder Structure

```text
src/
├── config/
├── modules/
│   ├── auth/
│   ├── users/
│   ├── patients/
│   ├── doctors/
│   ├── appointments/
│   ├── visits/
│   ├── triage/
│   ├── prescriptions/
│   ├── notifications/
│   └── audit/
├── common/
│   ├── middleware/
│   ├── guards/
│   ├── utils/
│   └── constants/
├── database/
│   ├── migrations/
│   ├── seeders/
│   └── models/
└── app.ts
```

This kind of module-oriented structure is easy to explain in interviews because it maps directly to business domains.

## Database Schema

A relational database fits well because healthcare workflow data is transactional, stateful, and highly connected. PostgreSQL is a good choice because it handles structured data, constraints, indexing, and JSON fields for semi-structured metadata well.

### Main entities
- users
- roles
- patients
- doctors
- departments
- doctor_schedules
- appointments
- visits
- triage_records
- medical_notes
- prescriptions
- prescription_items
- audit_logs

### Schema view

```text
users
- id (PK)
- name
- email
- password_hash
- role_id (FK)
- status
- created_at
- updated_at

roles
- id (PK)
- name

patients
- id (PK)
- user_id (FK)
- dob
- gender
- phone
- address
- blood_group
- emergency_contact
- created_at

doctors
- id (PK)
- user_id (FK)
- department_id (FK)
- specialization
- license_number
- experience_years
- created_at

departments
- id (PK)
- name
- description

doctor_schedules
- id (PK)
- doctor_id (FK)
- day_of_week
- start_time
- end_time
- slot_duration_mins

appointments
- id (PK)
- patient_id (FK)
- doctor_id (FK)
- scheduled_at
- type
- status
- created_by
- created_at

visits
- id (PK)
- appointment_id (FK, nullable)
- patient_id (FK)
- doctor_id (FK)
- visit_type
- status
- checked_in_at
- consultation_started_at
- completed_at

triage_records
- id (PK)
- visit_id (FK)
- chief_complaint
- symptoms_text
- temperature
- heart_rate
- systolic_bp
- diastolic_bp
- spo2
- pain_score
- severity_score
- ai_priority_score
- created_by
- created_at

medical_notes
- id (PK)
- visit_id (FK)
- doctor_id (FK)
- diagnosis
- notes
- follow_up_advice
- created_at

prescriptions
- id (PK)
- visit_id (FK)
- doctor_id (FK)
- instructions
- created_at

prescription_items
- id (PK)
- prescription_id (FK)
- medicine_name
- dosage
- frequency
- duration_days

audit_logs
- id (PK)
- actor_user_id (FK)
- entity_type
- entity_id
- action
- metadata_json
- created_at
```

## ER Relationship Highlights

| Relationship | Description |
|---|---|
| users -> roles | Each user belongs to one role |
| patients -> users | A patient profile maps to one platform user |
| doctors -> users | A doctor profile maps to one platform user |
| doctors -> departments | Each doctor belongs to one department |
| appointments -> patients/doctors | Appointment connects a patient and doctor |
| visits -> appointments | A visit may originate from an appointment or a walk-in |
| triage_records -> visits | Each visit can have an associated triage record |
| medical_notes -> visits | Consultation notes belong to a visit |
| prescriptions -> visits | Prescription belongs to a visit |
| prescription_items -> prescriptions | One prescription has many items |

## Authorization Design

Role-based access control is one of the strongest engineering points in this project. It makes the system feel production-oriented rather than just CRUD.

### Authorization rules
- Patients can access only their own profile, appointments, visits, and prescriptions.
- Doctors can access only assigned visits and permitted patient records.
- Receptionists can manage intake, queue, and scheduling but should not edit final diagnosis.
- Admins can manage users, departments, schedules, and operational settings.

### Example route protection

```text
POST   /auth/register
POST   /auth/login
GET    /patients/me                     -> patient only
POST   /appointments                    -> patient/receptionist
PATCH  /appointments/:id/cancel         -> owner/receptionist/admin
POST   /visits/check-in                 -> receptionist
POST   /triage/:visitId                 -> receptionist/nurse/admin
GET    /doctor/queue                    -> doctor only
POST   /consultations/:visitId/notes    -> doctor only
POST   /prescriptions/:visitId          -> doctor only
GET    /admin/users                     -> admin only
```

## API Design Snapshot

### Example endpoints

| Module | Endpoint | Purpose |
|---|---|---|
| Auth | `POST /auth/login` | User login |
| Patients | `GET /patients/:id` | Fetch patient profile |
| Appointments | `POST /appointments` | Book appointment |
| Visits | `POST /visits/check-in` | Mark patient arrival |
| Triage | `POST /triage/:visitId` | Save complaint and vitals |
| Clinical | `POST /consultations/:visitId/notes` | Add diagnosis and notes |
| Prescriptions | `POST /prescriptions/:visitId` | Generate prescription |
| Admin | `GET /admin/analytics` | Operational dashboard |

## Queue and Status Model

Queueing is important in hospital workflows because waiting-time management strongly affects throughput and experience.

### Visit lifecycle
```text
BOOKED -> CHECKED_IN -> IN_TRIAGE -> IN_QUEUE -> IN_CONSULTATION -> COMPLETED
                     \-> CANCELLED
```

### Why this matters
- It gives a clear state machine to discuss in interviews.
- It simplifies audit logs and notifications.
- It makes AI prioritization easier later because the queue state is explicit.

## How AI/ML Fits In

The AI layer should be presented as a decision-support assistant, not an autonomous medical decision engine. That framing is safer, more realistic, and stronger in interviews.

### AI/ML opportunities

#### 1. NLP for symptom understanding
Free-text complaint fields such as “chest pain since morning” or “high fever and dizziness” can be parsed into normalized symptom features. NLP can extract entities such as symptom type, duration, intensity, and urgency hints.

#### 2. Triage scoring model
A supervised ML model can use vitals, pain score, age band, and complaint-derived features to predict a priority level or escalation score. The output can help staff identify patients who may need quicker attention.

#### 3. Queue prioritization
Instead of using only arrival time, the system can recommend dynamic ordering based on symptom severity, abnormal vitals, and predicted urgency. This aligns with real queue optimization concerns in emergency care operations.

#### 4. Explainability layer
Interviewers usually like this part. The system should show why a patient was prioritized, such as low oxygen saturation, high fever, chest pain keywords, or elevated heart rate. This keeps the AI feature grounded and auditable.

### Proposed AI pipeline

```text
Input Data
  -> symptom text
  -> vitals
  -> age / demographics
  -> visit context

Preprocessing
  -> clean text
  -> tokenize / embed complaint text
  -> normalize vitals
  -> create structured features

Model Layer
  -> NLP feature extractor
  -> triage classifier / risk score model

Output
  -> ai_priority_score
  -> suggested triage label
  -> explanation factors
  -> escalation recommendation
```

### Good interview framing
Say that the first production version uses rule-based thresholds plus clinical heuristics, and the ML model is introduced only after enough labeled visit data is available. That sounds much more mature than claiming immediate deep-learning automation.

## Tech Stack Suggestion

| Layer | Recommended option |
|---|---|
| Frontend | React / Next.js |
| Backend | Node.js with Express or NestJS |
| Database | PostgreSQL |
| ORM | Prisma or TypeORM |
| Auth | JWT + refresh token |
| Validation | Zod / class-validator |
| AI/ML service | Python FastAPI service |
| ML libraries | scikit-learn, PyTorch, spaCy, transformers |
| Infra | Docker, Nginx, Render/Railway/AWS |

This stack is interview-friendly because each choice has a clear reason and clean module separation.

## Deployment View

```text
[Frontend: React/Next.js]
          |
          v
[Backend API: Node.js]
          |
          +----------------------+
          |                      |
          v                      v
[PostgreSQL]             [Python AI Service]
          |
          v
   [Audit/Logs/Backups]
```

## Non-Functional Requirements

### Security
- Password hashing with bcrypt/argon2.
- JWT authentication with short-lived access token.
- Role-based authorization checks on every protected route.
- Sensitive action logging.
- Input validation and sanitization.

### Reliability
- Transaction-safe visit state updates.
- Soft delete or archived status for sensitive records.
- Retry-safe notification flows.
- Strong DB constraints and indexes.

### Scalability
- Start with modular monolith.
- Extract AI service first if CPU-heavy inference increases.
- Add Redis later for queue caching and background jobs.

## Interview Pitch

A concise way to explain the project:

> Built a healthcare workflow platform that handles patient registration, scheduling, triage, queue management, doctor consultations, and prescriptions using a modular backend architecture, PostgreSQL schema design, and role-based security. The roadmap extends it with AI-assisted triage using NLP and structured vitals for priority recommendations.

A stronger version for system-design rounds:

> Designed the system as a modular monolith to keep development simple while preserving service boundaries for auth, scheduling, visits, clinical records, and AI. The data layer uses normalized relational models for transactional integrity, while future AI components consume triage and complaint data to recommend urgency levels with explainable outputs.

## Roadmap

### Phase 1
- Auth and role management
- Patient and doctor management
- Appointment booking
- Visit and queue lifecycle
- Consultation and prescriptions

### Phase 2
- Dashboard analytics
- Notifications and reminders
- Department-wise reporting
- Audit logs and admin controls

### Phase 3
- NLP complaint extraction
- Triage ML scoring
- Queue prioritization recommendations
- Explainability and safety checks

## Resume Bullet Ideas

- Designed and built a healthcare workflow backend supporting role-based access, appointment scheduling, visit lifecycle tracking, consultation notes, and prescriptions.
- Modeled a relational PostgreSQL schema for patients, doctors, visits, triage, and prescription workflows with audit-friendly state transitions.
- Planned a modular AI-assisted triage extension using NLP for symptom extraction and ML-based urgency scoring to support queue prioritization.

## Final Positioning

This project should be positioned as a serious full-stack systems project with healthcare workflow depth, not just as a hospital CRUD app. The AI/ML section adds ambition, but the core engineering value comes from strong schema design, authorization, lifecycle modeling, and system architecture.
