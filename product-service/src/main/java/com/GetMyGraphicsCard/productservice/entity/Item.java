package com.GetMyGraphicsCard.productservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document("items")
public class Item {

    private String title;
    private String link;
    private String image;
    private String lprice;
    @Id
    private String productId;

    @Override
    public String toString() {
        return "{" +
                "title=" + title + '\'' +
                ", link='" + link + '\'' +
                ", image='" + image + '\'' +
                ", lprice='" + lprice + '\'' +
                ", productId='" + productId + '\'' +
                '}';
    }
}
