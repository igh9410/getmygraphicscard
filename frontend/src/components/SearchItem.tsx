import React from "react";
import ItemModel from "../models/ItemModel";
import Card from "@mui/material/Card";
import { IconButton } from "@mui/material";
import "./SearchItem.css";
import { StarBorder } from "@mui/icons-material";

const SearchItem: React.FC<{ item: ItemModel }> = (props) => {
  return (
    <div className="itemSection">
      <p>
        <img src={props.item.image} alt="Item" width="400" height="400" />
        <br />
        <a href={props.item.link}>{props.item.title}</a>
        <br />
        Price: {props.item.lprice}원(Won)
        <br />
        <IconButton>
          <StarBorder />
        </IconButton>
      </p>
    </div>
  );
};

export default SearchItem;
