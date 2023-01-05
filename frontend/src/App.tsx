import React from "react";
import "./App.css";
import Header from "./components/Header";
import Footer from "./components/Footer";
import HomePage from "./pages/HomePage";
import { Route, Routes } from "react-router-dom";
import SearchItemsPage from "./pages/SearchItemsPage";

function App() {
  return (
    <div className="App">
      <Header />
      <Routes>
        <Route path="/" element={<HomePage />} />;
        <Route path="/items" element={<SearchItemsPage />} />;
      </Routes>
      <Footer />
    </div>
  );
}

export default App;
