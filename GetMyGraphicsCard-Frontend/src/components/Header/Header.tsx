import React from 'react';
import styles from './Header.module.scss';
import { Search } from '@components/Search';
import { Button } from '@mui/material';

export function Header() {
  return (
    <div className={styles.header}>
      <Search />
      <div className={styles.buttonWrapper}>
        <Button
          style={{
            width: '6.3125rem',
            height: '2.875rem',
            marginRight: '1.25rem',
          }}
          variant="contained"
          size="medium"
        >
          Sign Up
        </Button>
        <Button
          style={{ width: '6.3125rem', height: '2.875rem' }}
          variant="contained"
          size="medium"
        >
          Sign In
        </Button>
      </div>
    </div>
  );
}
