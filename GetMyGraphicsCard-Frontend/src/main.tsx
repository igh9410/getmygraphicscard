import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import { AppProvider, Routes } from '@providers';
import { CssBaseline } from '@mui/material';

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <CssBaseline>
      <AppProvider>
        <Routes />
      </AppProvider>
    </CssBaseline>
  </React.StrictMode>
);
