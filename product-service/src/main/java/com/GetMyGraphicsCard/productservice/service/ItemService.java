package com.GetMyGraphicsCard.productservice.service;

import com.GetMyGraphicsCard.productservice.dto.ItemResponse;

import java.util.List;

public interface ItemService {

    public List<ItemResponse> getAllItems();

    public List<ItemResponse> findItemsInPriceRange(int lowest, int highest);

    public List<ItemResponse> findAllItemsByTitle(String title);




}
