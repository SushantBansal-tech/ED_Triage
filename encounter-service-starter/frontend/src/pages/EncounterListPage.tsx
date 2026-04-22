import { Alert, CircularProgress, Stack, Typography } from '@mui/material';
import { useQuery } from '@tanstack/react-query';
import { fetchEncounters } from '../api/encountersApi';
import { EncounterTable } from '../features/encounters/EncounterTable';

export function EncounterListPage() {
  const { data, isLoading, isError } = useQuery({ queryKey: ['encounters'], queryFn: fetchEncounters });

  if (isLoading) return <CircularProgress />;
  if (isError) return <Alert severity="error">Failed to load encounters.</Alert>;

  return (
    <Stack spacing={2}>
      <Typography variant="h4">Active Queue</Typography>
      <EncounterTable encounters={data ?? []} />
    </Stack>
  );
}
