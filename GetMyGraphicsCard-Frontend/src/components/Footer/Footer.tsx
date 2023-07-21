import { IconButton, Link } from '@mui/material';
import React from 'react';
import EmailIcon from '@mui/icons-material/Email';
import GitHubIcon from '@mui/icons-material/GitHub';
import LinkedInIcon from '@mui/icons-material/LinkedIn';
import styles from './Footer.module.scss';

export function Footer() {
  return (
    <footer className={styles.footer}>
      <Link href="mailto:athanasia9410@gmail.com">
        <IconButton
          style={{
            width: '3.6875rem',
            height: '3.6875rem',
          }}
        >
          <EmailIcon style={{ width: '100%', height: '100%' }} />
        </IconButton>
      </Link>
      <Link href="https://github.com/igh9410">
        <IconButton
          style={{
            width: '3.6875rem',
            height: '3.6875rem',
          }}
        >
          <GitHubIcon style={{ width: '100%', height: '100%' }} />
        </IconButton>
      </Link>
      <Link href="https://www.linkedin.com/in/geonhyuk-im-6966421b0/">
        <IconButton
          style={{
            width: '3.6875rem',
            height: '3.6875rem',
          }}
        >
          <LinkedInIcon style={{ width: '100%', height: '100%' }} />
        </IconButton>
      </Link>
    </footer>
  );
}
