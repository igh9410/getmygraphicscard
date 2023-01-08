package com.GetMyGraphicsCard.productservice.controller;

import com.GetMyGraphicsCard.productservice.dto.ItemResponse;
import com.GetMyGraphicsCard.productservice.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public List<ItemResponse> getAllItems(@RequestParam(defaultValue = "0") Integer pageNo,
                                          @RequestParam(defaultValue = "20") Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size);
        return itemService.getAllItems(pageable);
    }

    @GetMapping("/search")
    public List<ItemResponse> findAllItemsByTitle(@RequestParam String title, @RequestParam(defaultValue = "0") Integer pageNo,
                                                  @RequestParam(defaultValue = "20") Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size);
        return itemService.findAllItemsByTitle(title, pageable);
    }

    @GetMapping("/price")
    public List<ItemResponse> findAllItemsByPriceRange(@RequestParam int lowest, @RequestParam int highest) throws Exception {
        return itemService.findItemsInPriceRange(lowest, highest);
    }

    @GetMapping("{id}")
    public ItemResponse findItemById(@PathVariable String id) throws Exception {
        return itemService.findItemById(id);
    }

}
