package com.GetMyGraphicsCard.subscriptionservice.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PriceAlert {


    private String productId;
    private String title;
    private String link;

    private int lprice;

    @Override
    public String toString() {
        return "PriceAlert{" +
                "productId='" + productId + '\'' +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", lprice=" + lprice +
                '}';
    }
}
