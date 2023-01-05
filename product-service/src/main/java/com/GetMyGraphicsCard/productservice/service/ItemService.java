package com.GetMyGraphicsCard.productservice.service;

import com.GetMyGraphicsCard.productservice.dto.ItemResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {

    public List<ItemResponse> getAllItems(Pageable pageable);

    public List<ItemResponse> findItemsInPriceRange(int lowest, int highest) throws Exception;

    public List<ItemResponse> findAllItemsByTitle(String title, Pageable pageable);

    public ItemResponse findItemById(String id) throws Exception;




}
