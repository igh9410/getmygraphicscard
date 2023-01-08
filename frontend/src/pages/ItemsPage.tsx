import { useEffect, useState } from "react";
import ItemModel from "../models/ItemModel";
import SpinnerLoader from "../utils/SpinnerLoader";
import { Box } from "@mui/system";
import SearchItem from "../components/SearchItem";
import "./ItemsPage.css";
import { useSearchParams } from "react-router-dom";

function ItemsPage() {
  const [items, setItems] = useState<ItemModel[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [httpError, setHttpError] = useState(null);
  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage] = useState(20);
  const [searchParams, setSearchParams] = useSearchParams();

  useEffect(() => {
    const fetchItems = async () => {
      const baseUrl: string = "http://localhost:8888/api/items/search";
      console.log(searchParams.get("title"));
      const url: string = `${baseUrl}?title=${searchParams.get(
        "title"
      )}&pageNo=${currentPage - 1}&size=${itemsPerPage}`;

      const response = await fetch(url);

      if (!response.ok) {
        throw new Error("Something went wrong!");
      }

      const responseData = await response.json();

      const loadedItems: ItemModel[] = [];

      for (const key in responseData) {
        loadedItems.push({
          title: responseData[key].title,
          link: responseData[key].link,
          image: responseData[key].image,
          lprice: responseData[key].lprice,
        });
      }

      setItems(loadedItems);
      setIsLoading(false);
    };
    fetchItems().catch((error: any) => {
      setIsLoading(false);
      setHttpError(error.message);
    });
  }, [searchParams]);

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
    </div>
  );
}

export default ItemsPage;
