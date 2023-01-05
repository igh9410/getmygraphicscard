import SearchIcon from "@mui/icons-material/Search";
import "./SearchBar.css";
import { Route, useNavigate } from "react-router-dom";
import { IconButton } from "@mui/material";
import React, { useState } from "react";
import SearchItemsPage from "../pages/SearchItemsPage";

function SearchBar() {
  const [searchInput, setSearchInput] = useState("");
  const navigate = useNavigate();

  function handleSubmit(e: React.FormEvent<HTMLFormElement>) {
    e.preventDefault();
    navigate("/items");
  }

  return (
    <div className="search">
      <form onSubmit={handleSubmit}>
        <input
          id="title"
          type="text"
          placeholder="Search"
          onChange={(e) => setSearchInput(e.target.value)}
        />
        <IconButton type="submit">
          <SearchIcon />
        </IconButton>
      </form>
    </div>
  );
}

// Make a request

export default SearchBar;
