import { PropsWithChildren } from 'react';
import { AppBar, Box, Drawer, List, ListItemButton, ListItemText, Toolbar, Typography } from '@mui/material';
import { Link, useLocation } from 'react-router-dom';

const items = [
  { label: 'Queue', to: '/' },
  { label: 'New Encounter', to: '/encounters/new' }
];

export function AppLayout({ children }: PropsWithChildren) {
  const location = useLocation();

  return (
    <Box sx={{ display: 'flex', minHeight: '100vh' }}>
      <Drawer variant="permanent" sx={{ width: 240, '& .MuiDrawer-paper': { width: 240, boxSizing: 'border-box' } }}>
        <Toolbar>
          <Typography variant="h6">Encounter OS</Typography>
        </Toolbar>
        <List>
          {items.map((item) => (
            <ListItemButton key={item.to} component={Link} to={item.to} selected={location.pathname === item.to}>
              <ListItemText primary={item.label} />
            </ListItemButton>
          ))}
        </List>
      </Drawer>
      <Box sx={{ flexGrow: 1 }}>
        <AppBar position="static" color="transparent" elevation={0}>
          <Toolbar>
            <Typography variant="h5" fontWeight={700}>Encounter Dashboard</Typography>
          </Toolbar>
        </AppBar>
        <Box sx={{ p: 3 }}>{children}</Box>
      </Box>
    </Box>
  );
}
