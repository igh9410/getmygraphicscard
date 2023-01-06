package com.GetMyGraphicsCard.productservice.repository;

import com.GetMyGraphicsCard.productservice.entity.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {

    List<Item> findAllBy(TextCriteria criteria, Pageable pageable);



    @Query(value = "{ 'lprice' : { $gte : ?0, $lte : ?1}}")
    List<Item> findItemByLpriceBetween(int lowest, int highest);


}
