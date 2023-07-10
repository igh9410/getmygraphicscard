package com.getmygraphicscard.productservice.service;

import com.getmygraphicscard.productservice.dto.ItemResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemService {

    Page<ItemResponse> getAllItems(Pageable pageable);

    List<ItemResponse> findItemsInPriceRange(int lowest, int highest) throws Exception;

    Page<ItemResponse> findAllItemsByTitle(String title, Pageable pageable);

    ItemResponse findItemById(String id) throws Exception;


    Page<ItemResponse> findItemsByTitleAndPriceRange(String title, int lowest, int highest, Pageable pageable) throws Exception;
}
