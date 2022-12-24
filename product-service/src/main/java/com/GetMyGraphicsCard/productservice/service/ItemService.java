package com.GetMyGraphicsCard.productservice.service;

import com.GetMyGraphicsCard.productservice.dto.ItemResponse;

import java.util.List;

public interface ItemService {

    public List<ItemResponse> getAllItems();

    public List<ItemResponse> findItemsInPriceRange(int lowest, int highest) throws Exception;

    public List<ItemResponse> findAllItemsByTitle(String title);

    public ItemResponse findItemById(String id) throws Exception;




}
