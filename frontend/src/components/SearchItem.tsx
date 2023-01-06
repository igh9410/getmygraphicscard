import React from "react";
import ItemModel from "../models/ItemModel";
import Card from "@mui/material/Card";
import { CardActionArea, CardMedia, Typography } from "@mui/material";
import "./SearchItem.css";

const SearchItem: React.FC<{ item: ItemModel }> = (props) => {
  return (
    <div className="itemSection">
      <Card sx={{ width: 400, height: 400 }}>
        <CardMedia
          sx={{ width: 300, height: 300 }}
          image={props.item.image}
          title="item"
        />
        <CardActionArea>
          <Typography gutterBottom variant="h5" component="div">
            <a href={props.item.link}>{props.item.title}</a>
          </Typography>
          <Typography variant="body2" color="text.secondary">
            Price: {props.item.lprice}원(Won)
          </Typography>
        </CardActionArea>
      </Card>
    </div>
  );
};

export default SearchItem;
