import { IconButton, Link } from "@mui/material";
import React from "react";
import EmailIcon from "@mui/icons-material/Email";
import GitHubIcon from "@mui/icons-material/GitHub";
import LinkedInIcon from "@mui/icons-material/LinkedIn";
import "./Footer.css";

function Footer() {
  return (
    <footer>
      <Link href="mailto:athanasia9410@gmail.com">
        <IconButton>
          <EmailIcon />
        </IconButton>
      </Link>
      <Link href="https://github.com/igh9410">
        <IconButton>
          <GitHubIcon />
        </IconButton>
      </Link>
      <Link href="https://www.linkedin.com/in/geonhyuk-im-6966421b0/">
        <IconButton>
          <LinkedInIcon />
        </IconButton>
      </Link>
    </footer>
  );
}

export default Footer;
