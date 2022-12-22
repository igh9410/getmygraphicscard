package com.GetMyGraphicsCard.productservice.service;

import com.GetMyGraphicsCard.productservice.entity.Item;
import com.GetMyGraphicsCard.productservice.repository.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestPropertySource(locations = "/application-test.properties")
@SpringBootTest
public class ItemServiceTest {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemServiceImpl itemService;

    @Test
    @DisplayName("Generated query: Search for \"RTX 3060\", should return 2")
    void findAllItemsByTitle() {
        // Simulate predefined index
        itemRepository.insert(List.of(
                new Item("RTX 3050"),
                new Item("RTX 3060"),
                new Item("RTX 3060Ti"),
                new Item("RTX 3070")
        ));


        assertThat(items)
                .hasSize(2)
                .extracting("title")
                .containsOnly("RTX 3060");

    }



}
