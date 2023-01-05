import React from "react";
import "./SpinnerLoader.css";
import Box from "@mui/material/Box";
import { CircularProgress } from "@mui/material";

function SpinnerLoader() {
  return (
    <Box sx={{ display: "flex " }}>
      <CircularProgress />
    </Box>
  );
}

export default SpinnerLoader;
