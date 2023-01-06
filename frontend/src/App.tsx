import React from "react";

import Header from "./components/Header";
import Footer from "./components/Footer";
import HomePage from "./pages/HomePage";
import { Route, Routes } from "react-router-dom";
import SearchItemsPage from "./pages/SearchItemsPage";
import Container from "./layouts/Container";
import "./App.css";

function App() {
  return (
    <div className="App">
      <Container>
        <Header />

        <Routes>
          <Route path="/" element={<HomePage />} />;
          <Route path="/items" element={<SearchItemsPage />} />;
        </Routes>

        <Footer />
      </Container>
    </div>
  );
}

export default App;
