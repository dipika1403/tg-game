package com.galvanize.itemservice.repository;

import com.galvanize.itemservice.entity.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> {
    Iterable<Item> findByName(String name);
}
