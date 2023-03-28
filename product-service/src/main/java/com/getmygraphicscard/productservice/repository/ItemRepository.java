package com.GetMyGraphicsCard.productservice.repository;

import com.GetMyGraphicsCard.productservice.entity.Item;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {

    Page<Item> findAll(Pageable pageable);
    Page<Item> findAllBy(TextCriteria criteria, Pageable pageable);

    Optional<Item> findByLink(String Link);

    @Query(value = "{ 'lprice' : { $gte : ?0, $lte : ?1}}")
    List<Item> findItemByLpriceBetween(int lowest, int highest);


}
