import React, { useEffect, useState } from "react";
import ItemModel from "../models/ItemModel";
import SpinnerLoader from "../utils/SpinnerLoader";
import { Box } from "@mui/system";
import SearchItem from "../components/SearchItem";
import "./ItemsPage.css";
import { useNavigate, useSearchParams } from "react-router-dom";
import Pagination from "@mui/material/Pagination";

function ItemsPage() {
  const [items, setItems] = useState<ItemModel[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [httpError, setHttpError] = useState(null);
  const [itemsPerPage] = useState(20);
  const [searchParams, setSearchParams] = useSearchParams();
  const [totalPages, setTotalPages] = useState(0);

  const handlePageChange = (e: React.ChangeEvent<unknown>, value: number) => {
    let redirectionURL =
      window.location.pathname +
      "?title=" +
      searchParams.get("title") +
      "&pageNo=" +
      value.toString();
    window.location.assign(redirectionURL);
  };

  useEffect(() => {
    const fetchItems = async () => {
      const baseUrl: string = "http://localhost:8888/api/items/search";

      const url: string = `${baseUrl}?title=${searchParams.get(
        "title"
      )}&pageNo=${Number(searchParams.get("pageNo")) - 1}&size=${itemsPerPage}`;

      const response = await fetch(url);

      if (!response.ok) {
        throw new Error("Something went wrong!");
      }

      const responseJson = await response.json();
      const responseData = responseJson.content;

      setTotalPages(responseJson.totalPages);

      const loadedItems: ItemModel[] = [];

      for (const key in responseData) {
        loadedItems.push({
          title: responseData[key].title,
          link: responseData[key].link,
          image: responseData[key].image,
          lprice: responseData[key].lprice,
        });
      }
      console.log(loadedItems);
      setItems(loadedItems);
      setIsLoading(false);
    };
    fetchItems().catch((error: any) => {
      setIsLoading(false);
      setHttpError(error.message);
    });
  }, [searchParams, totalPages]);

  if (isLoading) {
    return <SpinnerLoader />;
  }

  if (httpError) {
    return (
      <Box sx={{ border: "1px dashed grey" }}>
        <p>{httpError}</p>
      </Box>
    );
  }

  return (
    <div>
      <div className="itemBox">
        {items.map((item) => (
          <SearchItem item={item} key={item.link} />
        ))}
      </div>
      <div className="pagination">
        <Pagination
          count={totalPages}
          page={Number(searchParams.get("pageNo"))}
          color="primary"
          onChange={handlePageChange}
        />
      </div>
    </div>
  );
}

export default ItemsPage;
