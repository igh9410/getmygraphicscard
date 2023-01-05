import { useEffect, useState } from "react";
import ItemModel from "../models/ItemModel";
import SpinnerLoader from "../utils/SpinnerLoader";
import { Box } from "@mui/system";

function SearchItemsPage() {
  const [items, setItems] = useState<ItemModel[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [httpError, setHttpError] = useState(null);

  useEffect(() => {
    const fetchItems = async () => {
      const baseUrl: string = "http://localhost:8888/api/items";

      const url: string = `${baseUrl}?pageNo=0&size=20`;

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
      console.log(loadedItems);
      setItems(loadedItems);
      setIsLoading(false);
    };
    fetchItems().catch((error: any) => {
      setIsLoading(false);
      setHttpError(error.message);
    });
  }, []);

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
      <Box></Box>
    </div>
  );
}

export default SearchItemsPage;
