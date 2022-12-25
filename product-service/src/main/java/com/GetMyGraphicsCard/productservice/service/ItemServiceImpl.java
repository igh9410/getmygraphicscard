package com.GetMyGraphicsCard.productservice.service;

import com.GetMyGraphicsCard.productservice.dto.ItemResponse;
import com.GetMyGraphicsCard.productservice.entity.Item;
import com.GetMyGraphicsCard.productservice.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
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
    public List<ItemResponse> getAllItems() {
        List<Item> items = itemRepository.findAll();
        return  items.parallelStream().map(this::mapToItemResponse).toList();
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
    public List<ItemResponse> findAllItemsByTitle(String title) {
        Sort sort = Sort.by(title);
        TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matchingAny(title).caseSensitive(false);
        log.info("Finding items by title {}..", title);
        List<Item> items = itemRepository.findAllBy(textCriteria, sort);
        return items.parallelStream().map(this::mapToItemResponse).toList();
    }

    @Override
    public ItemResponse findItemById(String id) throws Exception {
        Optional<Item> result = itemRepository.findById(id);
        Item item = null;

        if (result.isPresent()) {
            item = result.get();
        } else {
            throw new Exception("Item not found .. ");
        }
        log.info("Getting ProductId {} info..", id);
        return mapToItemResponse(item);
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
