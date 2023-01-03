import React, { useEffect, useState } from "react";

import "./App.css";
import Header from "./components/Header";
import axios from "axios";

function App() {
  useEffect(() => {
    const url = "http://localhost:8888/api/items";
    axios.get(url).then((response) => {
      console.log(response.data);
    });
  });
  return (
    <div className="App">
      <Header />
    </div>
  );
}

export default App;
