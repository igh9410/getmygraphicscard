package com.GetMyGraphicsCard.productservice.controller;

import com.GetMyGraphicsCard.productservice.dto.ItemResponse;
import com.GetMyGraphicsCard.productservice.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping
    public List<ItemResponse> getAllItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/search")
    public List<ItemResponse> findAllItemsByTitle(@RequestParam String title) {
        return itemService.findAllItemsByTitle(title);
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
