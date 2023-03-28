package com.GetMyGraphicsCard.productservice.service;

import com.GetMyGraphicsCard.productservice.dto.ItemResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {

    public Page<ItemResponse> getAllItems(Pageable pageable);

    public List<ItemResponse> findItemsInPriceRange(int lowest, int highest) throws Exception;

    public Page<ItemResponse> findAllItemsByTitle(String title, Pageable pageable);

    public ItemResponse findItemById(String id) throws Exception;




}
