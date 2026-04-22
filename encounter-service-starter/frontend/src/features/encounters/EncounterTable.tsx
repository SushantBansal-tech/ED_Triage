import { Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Button } from '@mui/material';
import { Link } from 'react-router-dom';
import { Encounter } from '../../types/encounter';
import { StatusChip } from '../../components/StatusChip';

export function EncounterTable({ encounters }: { encounters: Encounter[] }) {
  return (
    <TableContainer component={Paper}>
      <Table>
        <TableHead>
          <TableRow>
            <TableCell>Encounter</TableCell>
            <TableCell>Patient</TableCell>
            <TableCell>Status</TableCell>
            <TableCell>Class</TableCell>
            <TableCell>Priority</TableCell>
            <TableCell>Location</TableCell>
            <TableCell>Action</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {encounters.map((encounter) => (
            <TableRow key={encounter.id} hover>
              <TableCell>{encounter.encounterNumber}</TableCell>
              <TableCell>{encounter.patientName}</TableCell>
              <TableCell><StatusChip status={encounter.status} /></TableCell>
              <TableCell>{encounter.encounterClass}</TableCell>
              <TableCell>{encounter.priority}</TableCell>
              <TableCell>{encounter.location}</TableCell>
              <TableCell>
                <Button component={Link} to={`/encounters/${encounter.id}`} size="small">View</Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
}
