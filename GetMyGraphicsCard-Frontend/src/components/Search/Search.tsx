import React from 'react';
import styles from './Search.module.scss';
import search from '@assets/img/icons/SearchIcon.svg';

export function Search() {
  return (
    <div className={styles.searchBox}>
      <span className={styles.svgContainer}>
        <button className={styles.searchIconFrame}>
          <img src={search} alt="Search Icon" />
        </button>
      </span>
      <input
        className={styles.inputBox}
        type="text"
        placeholder="Search for graphics cards..."
      />
    </div>
  );
}
