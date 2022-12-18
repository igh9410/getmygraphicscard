package com.GetMyGraphicsCard.productservice.repository;

import com.GetMyGraphicsCard.productservice.entity.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface ItemRepository extends MongoRepository<Item, String> {

    @Query("{title:'?0'")
    Item findItemByTitle(String title);

}
