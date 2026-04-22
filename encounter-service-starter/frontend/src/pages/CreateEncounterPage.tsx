import { Button, MenuItem, Paper, Stack, TextField, Typography } from '@mui/material';
import { useMutation } from '@tanstack/react-query';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { createEncounter } from '../api/encountersApi';

export function CreateEncounterPage() {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    patientId: '',
    patientName: '',
    encounterClass: 'EMERGENCY',
    priority: 'HIGH',
    location: 'ER-01',
    reasonForVisit: ''
  });

  const mutation = useMutation({
    mutationFn: createEncounter,
    onSuccess: (data) => navigate(`/encounters/${data.id}`)
  });

  return (
    <Paper sx={{ p: 3, maxWidth: 720 }}>
      <Stack spacing={2}>
        <Typography variant="h4">New Encounter</Typography>
        <TextField label="Patient ID" value={form.patientId} onChange={(e) => setForm({ ...form, patientId: e.target.value })} />
        <TextField label="Patient Name" value={form.patientName} onChange={(e) => setForm({ ...form, patientName: e.target.value })} />
        <TextField select label="Encounter Class" value={form.encounterClass} onChange={(e) => setForm({ ...form, encounterClass: e.target.value })}>
          <MenuItem value="EMERGENCY">EMERGENCY</MenuItem>
          <MenuItem value="OUTPATIENT">OUTPATIENT</MenuItem>
          <MenuItem value="INPATIENT">INPATIENT</MenuItem>
        </TextField>
        <TextField select label="Priority" value={form.priority} onChange={(e) => setForm({ ...form, priority: e.target.value })}>
          <MenuItem value="LOW">LOW</MenuItem>
          <MenuItem value="MEDIUM">MEDIUM</MenuItem>
          <MenuItem value="HIGH">HIGH</MenuItem>
          <MenuItem value="CRITICAL">CRITICAL</MenuItem>
        </TextField>
        <TextField label="Location" value={form.location} onChange={(e) => setForm({ ...form, location: e.target.value })} />
        <TextField label="Reason for Visit" value={form.reasonForVisit} onChange={(e) => setForm({ ...form, reasonForVisit: e.target.value })} multiline minRows={3} />
        <Button variant="contained" onClick={() => mutation.mutate(form)}>Create Encounter</Button>
      </Stack>
    </Paper>
  );
}
