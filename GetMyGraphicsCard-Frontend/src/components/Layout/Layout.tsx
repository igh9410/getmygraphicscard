import React from 'react';
import styles from './Layout.module.scss';
import { Header } from '@components/Header';
import { Footer } from '@components/Footer';

type LayoutProps = {
  children: React.ReactNode;
};

export const Layout = ({ children }: LayoutProps) => {
  return (
    <div className={styles.layout}>
      <div className={styles.container}>
        <Header />
        <div className={styles.content}>{children}</div>
        <Footer />
      </div>
    </div>
  );
};
