import React from "react";
import SearchBar from "./SearchBar";
import SignIn from "./SignIn";

export default function Header() {
  return (
    <header>
      <SearchBar />
      <SignIn />
    </header>
  );
}
