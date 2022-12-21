package com.GetMyGraphicsCard.productservice.repository;

import com.GetMyGraphicsCard.productservice.entity.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {

    @Query("{title:'?0'},{ $text: { $search: title}}")
    List<Item> findItemByTitle(String title);

}
