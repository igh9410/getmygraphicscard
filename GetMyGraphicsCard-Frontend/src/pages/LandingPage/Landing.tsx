import nvidia from '@assets/img/nvdia.jpg';
import styles from './Landing.module.scss';

export function Landing() {
  return (
    <>
      <img className={styles.imageContainer} src={nvidia} />
      <div className={styles.wrapper}>
        <section className={styles.greeting}>
          Welcome to getmygraphicscard.com!
          <br />
          Sign up and add your graphics cards into your wish-list!
          <br />
          You will get email notifications when your graphics cards in your
          wish-list become the lowest price available!
        </section>
      </div>
    </>
  );
}
