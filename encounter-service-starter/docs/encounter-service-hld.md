# Encounter Service HLD

## Goal
Build an encounter management module for emergency and hospital workflows. The service owns encounter creation, queue visibility, assignment, lifecycle updates, and detail retrieval.

## Frontend responsibilities
- Show active encounter queue
- Filter by status, class, priority, and location
- Create and edit encounter records
- View encounter detail and participant/location timeline
- Trigger workflow actions such as triage, start, transfer, discharge

## Backend responsibilities
- Expose encounter APIs
- Persist encounter, participant, location, and audit history
- Validate lifecycle transitions
- Publish DTOs optimized for dashboard reads

## Primary APIs
- `POST /api/encounters`
- `GET /api/encounters`
- `GET /api/encounters/{id}`
- `PATCH /api/encounters/{id}/status`
- `POST /api/encounters/{id}/participants`
- `POST /api/encounters/{id}/locations`

## Suggested next steps
- Add auth with JWT and role checks
- Add WebSocket updates for queue changes
- Add FHIR-compatible external DTO layer
- Add audit trail and soft delete strategy
