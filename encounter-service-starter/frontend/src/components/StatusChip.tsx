import { Chip } from '@mui/material';
import { EncounterStatus } from '../types/encounter';

const colorMap: Record<EncounterStatus, 'default' | 'primary' | 'warning' | 'success' | 'error'> = {
  PLANNED: 'default',
  ARRIVED: 'primary',
  TRIAGED: 'warning',
  IN_PROGRESS: 'primary',
  ON_HOLD: 'warning',
  DISCHARGED: 'success',
  COMPLETED: 'success',
  CANCELLED: 'error'
};

export function StatusChip({ status }: { status: EncounterStatus }) {
  return <Chip label={status} color={colorMap[status]} size="small" />;
}
