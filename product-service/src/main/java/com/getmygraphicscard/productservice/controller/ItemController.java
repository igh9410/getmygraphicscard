package com.getmygraphicscard.productservice.controller;

import com.getmygraphicscard.productservice.dto.ItemResponse;
import com.getmygraphicscard.productservice.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class ItemController {

    private final ItemService itemService;

    private Logger logger = LoggerFactory.getLogger("ItemController.class");

    @GetMapping
    public Page<ItemResponse> getAllItems(@RequestParam(defaultValue = "0") Integer pageNo,
                                          @RequestParam(defaultValue = "20") Integer size) {
        Pageable pageable = PageRequest.of(pageNo, size);
        return itemService.getAllItems(pageable);
    }

    @GetMapping("/search")
    public Page<ItemResponse> findAllItemsByTitle(@RequestParam String title, @RequestParam(defaultValue = "0") Integer pageNo,
                                                  @RequestParam(defaultValue = "20") Integer size,
                                                  @RequestParam(required = false) Integer lowest,
                                                  @RequestParam(required = false) Integer highest) throws Exception {
        Pageable pageable = PageRequest.of(pageNo, size);

        if (lowest != null && highest != null) {
            return itemService.findItemsByTitleAndPriceRange(title, lowest, highest, pageable);
        }
        return itemService.findAllItemsByTitle(title, pageable);
    }

    @GetMapping("{id}")
    public ItemResponse findItemById(@PathVariable String id) throws Exception {
        return itemService.findItemById(id);
    }

}
