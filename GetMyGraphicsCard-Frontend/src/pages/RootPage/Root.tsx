import { Layout } from '@components';
import React from 'react';
import { Outlet } from 'react-router-dom';

export function Root() {
  return (
    <Layout>
      <Outlet />
    </Layout>
  );
}
