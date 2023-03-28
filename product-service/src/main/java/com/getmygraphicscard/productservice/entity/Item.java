package com.getmygraphicscard.productservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.math.BigInteger;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document("items")
public class Item {

    @TextIndexed
    private String title;
    private String link;
    private String image;
    private int lprice;
    @Id
    private String productId;


    public Item(String title) { // Constructor for unit testing
        this.title = title;
    }
}
