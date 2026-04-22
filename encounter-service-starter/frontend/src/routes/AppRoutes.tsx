import { Route, Routes } from 'react-router-dom';
import { AppLayout } from '../layouts/AppLayout';
import { EncounterListPage } from '../pages/EncounterListPage';
import { EncounterDetailPage } from '../pages/EncounterDetailPage';
import { CreateEncounterPage } from '../pages/CreateEncounterPage';

export function AppRoutes() {
  return (
    <AppLayout>
      <Routes>
        <Route path="/" element={<EncounterListPage />} />
        <Route path="/encounters/new" element={<CreateEncounterPage />} />
        <Route path="/encounters/:id" element={<EncounterDetailPage />} />
      </Routes>
    </AppLayout>
  );
}
