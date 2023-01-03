import React from "react";
import SearchIcon from "@mui/icons-material/Search";
import "./SearchBar.css";
import { Link } from "react-router-dom";
import { IconButton } from "@mui/material";

interface SearchProps {
  placeholder: string;
}
function SearchBar({ placeholder }: SearchProps) {
  return (
    <div className="search">
      <div className="searchInputs">
        <input type="text" placeholder={placeholder} />
        <div className="searchIcon">
          <IconButton>
            <SearchIcon />
          </IconButton>
        </div>
      </div>
    </div>
  );
}

// Make a request

export default SearchBar;
