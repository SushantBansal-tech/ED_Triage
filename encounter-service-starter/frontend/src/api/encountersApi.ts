import { http } from './http';
import { CreateEncounterRequest, Encounter, UpdateStatusRequest } from '../types/encounter';

export async function fetchEncounters(): Promise<Encounter[]> {
  const { data } = await http.get<Encounter[]>('/encounters');
  return data;
}

export async function fetchEncounterById(id: string): Promise<Encounter> {
  const { data } = await http.get<Encounter>(`/encounters/${id}`);
  return data;
}

export async function createEncounter(payload: CreateEncounterRequest): Promise<Encounter> {
  const { data } = await http.post<Encounter>('/encounters', payload);
  return data;
}

export async function updateEncounterStatus(id: number, payload: UpdateStatusRequest): Promise<Encounter> {
  const { data } = await http.patch<Encounter>(`/encounters/${id}/status`, payload);
  return data;
}
