import SearchIcon from "@mui/icons-material/Search";
import "./SearchBar.css";
import { createSearchParams, useNavigate } from "react-router-dom";
import { IconButton } from "@mui/material";
import React, { useState } from "react";

function SearchBar() {
  const [searchInput, setSearchInput] = useState("");
  const navigate = useNavigate();

  function handleSubmit(e: React.FormEvent<HTMLFormElement>) {
    e.preventDefault();
    navigate({
      pathname: "/items/search",
      search: createSearchParams({
        title: searchInput,
        pageNo: "1",
      }).toString(),
    });
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
