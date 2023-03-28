package com.GetMyGraphicsCard.productservice.service;

import com.GetMyGraphicsCard.productservice.dto.ItemResponse;
import com.GetMyGraphicsCard.productservice.entity.Item;
import com.GetMyGraphicsCard.productservice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public Page<ItemResponse> getAllItems(Pageable pageable) {
        Page<Item> itemPage = itemRepository.findAll(pageable);
        return itemPage.map(item -> new ItemResponse(item.getTitle(), item.getLink(), item.getImage(), item.getLprice()));
    }

    @Override
    public List<ItemResponse> findItemsInPriceRange(int lowest, int highest) throws Exception {
        List<Item> items = itemRepository.findItemByLpriceBetween(lowest, highest);
        if (items == null) {
            throw new Exception("Items not found");
        }
        return items.parallelStream().map(this::mapToItemResponse).toList();
    }

    @Override
    public Page<ItemResponse> findAllItemsByTitle(String title, Pageable pageable) {
        TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matchingAny(title).caseSensitive(false);
        log.info("Finding items by title {}..", title);
        Page<Item> itemPage = itemRepository.findAllBy(textCriteria, pageable);
        return itemPage.map(item -> new ItemResponse(item.getTitle(), item.getLink(), item.getImage(), item.getLprice()));
    }

    @Override
    public ItemResponse findItemById(String id) throws Exception {
        Optional<Item> result = itemRepository.findById(id);
        if (result.isEmpty()) {
            throw new Exception("Item not found .. ");
        }
        log.info("Getting ProductId {} info..", id);
        return mapToItemResponse(result.get());
    }


    private ItemResponse mapToItemResponse(Item item) {
        return ItemResponse.builder()
                .title(item.getTitle())
                .link(item.getLink())
                .image(item.getImage())
                .lprice(item.getLprice())
                .build();
    }

}
