import React from "react";

import Header from "./components/Header";
import Footer from "./components/Footer";
import HomePage from "./pages/HomePage";
import { Route, Routes } from "react-router-dom";
import Container from "./layouts/Container";
import "./App.css";
import ItemsPage from "./pages/ItemsPage";
import ErrorPage from "./pages/ErrorPage";

function App(): JSX.Element {
  return (
    <div className="App">
      <Container>
        <Header />
        <Routes>
          <Route path="/" element={<HomePage />} />;
          <Route path="/items/search" element={<ItemsPage />} />;
          <Route path="*" element={<ErrorPage />} />;
        </Routes>
        <Footer />
      </Container>
    </div>
  );
}

export default App;
