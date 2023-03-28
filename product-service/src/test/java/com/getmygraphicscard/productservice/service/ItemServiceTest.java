package com.getmygraphicscard.productservice.service;

import com.getmygraphicscard.productservice.entity.Item;
import com.getmygraphicscard.productservice.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;


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


}
