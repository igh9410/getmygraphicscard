package com.GetMyGraphicsCard.productservice.service;

import com.GetMyGraphicsCard.productservice.dto.ItemResponse;
import com.GetMyGraphicsCard.productservice.entity.Item;
import com.GetMyGraphicsCard.productservice.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@TestPropertySource(locations = "/application-test.properties")
@SpringBootTest
public class ItemServiceTest {

    @Autowired
    private ItemServiceImpl itemService;

    @MockBean
    ItemRepository itemRepository;


    List<Item> graphicsCards = new ArrayList<Item>();

    @BeforeEach
    void setUp() {
        graphicsCards.add(new Item("RTX 3050"));
        graphicsCards.add(new Item("RTX 3060"));
        graphicsCards.add(new Item("RTX 3060Ti"));
        graphicsCards.add(new Item("RTX 3070"));
    }
    @Test
    @DisplayName("Perform full-text search on Item's title. Should return 2 for Item titles containing 3060")
    void findAllItemsByTitleTest() {
        when(itemRepository.saveAll(graphicsCards)).thenReturn(graphicsCards);
        TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matchingAny("3060").caseSensitive(false);
        Sort sort = Sort.by("3060");
        


        List<ItemResponse> itemResponses = itemService.findAllItemsByTitle("3060");
        for (ItemResponse i: itemResponses) {
            System.out.println(i.getTitle());
        }

    }


}
