package com.GetMyGraphicsCard.productservice.service;

import com.GetMyGraphicsCard.productservice.dto.ItemResponse;
import com.GetMyGraphicsCard.productservice.entity.Item;
import com.GetMyGraphicsCard.productservice.repository.ItemRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@Import({ItemServiceImpl.class})
public class ItemServiceTest {

    @InjectMocks
    ItemServiceImpl itemService;

    @Mock
    ItemRepository itemRepository;

    List<Item> graphicsCards = new ArrayList<>();

    @BeforeEach
    void setUp() {
        graphicsCards.add(new Item("RTX 3050"));
        graphicsCards.add(new Item("RTX 3060"));
        graphicsCards.add(new Item("RTX 3060Ti"));
        graphicsCards.add(new Item("RTX 3070"));
    }
    /*
    @Test
    @DisplayName("Perform full-text search on Item's title. Should return 2 for Item titles containing 3060")
    void findAllItemsByTitleTest() {
        when(itemRepository.save(any(Item.class))).thenAnswer(i -> i.getArguments()[0]);

        for (Item i: graphicsCards) {
            itemRepository.save(i);
        }
        verify(itemRepository, times(4)).save(any());

        TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matchingAny("3060").caseSensitive(false);

        Pageable pageable = PageRequest.of(0, 20);
        Page<Item> mockItems = Mockito.mock(Page.class);
        when(itemRepository.findAllBy(textCriteria, pageable)).thenReturn(mockItems);


    } */


}
