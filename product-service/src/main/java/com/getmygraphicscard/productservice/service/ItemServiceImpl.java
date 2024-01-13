package com.getmygraphicscard.productservice.service;

import com.getmygraphicscard.productservice.dto.ItemResponse;
import com.getmygraphicscard.productservice.entity.Item;
import com.getmygraphicscard.productservice.exception.InvalidPriceRangeException;
import com.getmygraphicscard.productservice.exception.ItemNotFoundException;
import com.getmygraphicscard.productservice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public Page<ItemResponse> getAllItems(Pageable pageable) {
        Page<Item> itemPage = itemRepository.findAll(pageable);
        return itemPage.map(item -> new ItemResponse(item.getProductId(), item.getTitle(), item.getLink(), item.getImage(), item.getLprice()));
    }

    @Override
    public List<ItemResponse> findItemsInPriceRange(int lowest, int highest) throws Exception {
        if (lowest > highest) {
            throw new InvalidPriceRangeException("Lowest price [" + lowest + "] can't exceed highest price [" + highest + "].");
        }

        List<Item> items = itemRepository.findItemByLpriceBetween(lowest, highest);
        return items.stream().map(this::mapToItemResponse).collect(Collectors.toList());
    }

    @Override
    public Page<ItemResponse> findAllItemsByTitle(String title, Pageable pageable) {
        TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matchingAny(title).caseSensitive(false);
        log.info("Finding items by title {}..", title);
        Page<Item> itemPage = itemRepository.findAllBy(textCriteria, pageable);
        return itemPage.map(item -> new ItemResponse(item.getProductId(), item.getTitle(), item.getLink(), item.getImage(), item.getLprice()));
    }

    @Override
    public ItemResponse findItemById(String id) throws Exception {
        return itemRepository.findById(id)
                .map(this::mapToItemResponse)
                .orElseThrow(() -> new ItemNotFoundException("Item with ID [" + id + "] not found."));
    }

    @Override
    public Page<ItemResponse> findItemsByTitleAndPriceRange(String title, int lowest, int highest, Pageable pageable) throws Exception {

        if (lowest > highest) {
            throw new InvalidPriceRangeException("Lowest price [" + lowest + "] can't exceed highest price [" + highest + "].");
        }

        Page<Item> itemPage = itemRepository.findItemsByTitleAndPriceRange(title, lowest, highest, pageable);

        return itemPage.map(this::mapToItemResponse);
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
