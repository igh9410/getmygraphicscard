package com.getmygraphicscard.productservice.repository;

import com.getmygraphicscard.productservice.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

    @Query("{$and: [{'$text': {$search: ?0}}, {'lprice': {$gte: ?1, $lte: ?2}}]}")
    List<Item> findItemsByTitleAndPriceRangeQuery(String title, int lowest, int highest);

    default Page<Item> findItemsByTitleAndPriceRange(String title, int lowest, int highest, Pageable pageable){
        List<Item> items = findItemsByTitleAndPriceRangeQuery(title, lowest, highest);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), items.size());
        return new PageImpl<>(items.subList(start, end), pageable, items.size());
    }

}
