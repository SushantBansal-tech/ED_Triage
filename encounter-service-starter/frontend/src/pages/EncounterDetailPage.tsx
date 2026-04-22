import { Alert, Button, Card, CardContent, CircularProgress, MenuItem, Stack, TextField, Typography } from '@mui/material';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useParams } from 'react-router-dom';
import { fetchEncounterById, updateEncounterStatus } from '../api/encountersApi';
import { EncounterStatus } from '../types/encounter';
import { useState } from 'react';

const statuses: EncounterStatus[] = ['PLANNED','ARRIVED','TRIAGED','IN_PROGRESS','ON_HOLD','DISCHARGED','COMPLETED','CANCELLED'];

export function EncounterDetailPage() {
  const { id = '' } = useParams();
  const qc = useQueryClient();
  const { data, isLoading, isError } = useQuery({ queryKey: ['encounter', id], queryFn: () => fetchEncounterById(id) });
  const [status, setStatus] = useState<EncounterStatus>('ARRIVED');
  const mutation = useMutation({
    mutationFn: (next: EncounterStatus) => updateEncounterStatus(Number(id), { status: next }),
    onSuccess: () => {
      qc.invalidateQueries({ queryKey: ['encounters'] });
      qc.invalidateQueries({ queryKey: ['encounter', id] });
    }
  });

  if (isLoading) return <CircularProgress />;
  if (isError || !data) return <Alert severity="error">Encounter not found.</Alert>;

  return (
    <Stack spacing={3}>
      <Typography variant="h4">Encounter {data.encounterNumber}</Typography>
      <Card>
        <CardContent>
          <Stack spacing={1}>
            <Typography><strong>Patient:</strong> {data.patientName}</Typography>
            <Typography><strong>Patient ID:</strong> {data.patientId}</Typography>
            <Typography><strong>Status:</strong> {data.status}</Typography>
            <Typography><strong>Reason:</strong> {data.reasonForVisit}</Typography>
            <Typography><strong>Location:</strong> {data.location}</Typography>
          </Stack>
        </CardContent>
      </Card>
      <Stack direction="row" spacing={2} alignItems="center">
        <TextField select label="Update status" value={status} onChange={(e) => setStatus(e.target.value as EncounterStatus)} sx={{ minWidth: 240 }}>
          {statuses.map((s) => <MenuItem key={s} value={s}>{s}</MenuItem>)}
        </TextField>
        <Button variant="contained" onClick={() => mutation.mutate(status)}>Save</Button>
      </Stack>
    </Stack>
  );
}
