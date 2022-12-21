package com.GetMyGraphicsCard.productservice.service;

import com.GetMyGraphicsCard.productservice.dto.ItemResponse;
import com.GetMyGraphicsCard.productservice.entity.Item;
import com.GetMyGraphicsCard.productservice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    @Override
    public List<ItemResponse> getAllItems() {
        List<Item> items = itemRepository.findAll();
        return  items.parallelStream().map(this::mapToItemResponse).toList();
    }

    @Override
    public List<ItemResponse> findItemsInPriceRange(int lowest, int highest) {
        return null;
    }
    /*
    @Override
    public List<ItemResponse> findAllItemsByTitle(String title) {
        List<Item> items = itemRepository.findAllById(title);
        return

    } */

    private ItemResponse mapToItemResponse(Item item) {
        return ItemResponse.builder()
                .title(item.getTitle())
                .link(item.getLink())
                .image(item.getImage())
                .lprice(Integer.parseInt(item.getLprice()))
                .build();
    }

}
