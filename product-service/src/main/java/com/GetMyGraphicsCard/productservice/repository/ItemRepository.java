package com.GetMyGraphicsCard.productservice.repository;

import com.GetMyGraphicsCard.productservice.entity.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository extends MongoRepository<Item, String> {
}
