export type EncounterStatus =
  | 'PLANNED'
  | 'ARRIVED'
  | 'TRIAGED'
  | 'IN_PROGRESS'
  | 'ON_HOLD'
  | 'DISCHARGED'
  | 'COMPLETED'
  | 'CANCELLED';

export interface Encounter {
  id: number;
  encounterNumber: string;
  patientId: string;
  patientName: string;
  status: EncounterStatus;
  encounterClass: string;
  priority: string;
  location: string;
  reasonForVisit: string;
  attendingPractitioner?: string;
  startedAt?: string;
  endedAt?: string;
  createdAt: string;
}

export interface CreateEncounterRequest {
  patientId: string;
  patientName: string;
  encounterClass: string;
  priority: string;
  location: string;
  reasonForVisit: string;
}

export interface UpdateStatusRequest {
  status: EncounterStatus;
}
