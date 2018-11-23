package com.galvanize.itemservice.service;

import com.galvanize.itemservice.entity.Item;
import com.galvanize.itemservice.exception.ObjectNotFoundException;
import com.galvanize.itemservice.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    public void deleteItemById(Long id) {
        if (!itemRepository.existsById(id)) {
            throw new ObjectNotFoundException(String.format("Object not found for deleting by id: %d", id));
        }
        itemRepository.deleteById(id);
    }

    public Item getItemById(Long id) {
        Optional<Item> optItem = itemRepository.findById(id);
        if (!optItem.isPresent()) {
            throw new ObjectNotFoundException(String.format("Object not found by id: %d", id));
        }
        return optItem.get();
    }

    public Iterable<Item> getItemsByName(String name) {
        return itemRepository.findByName(name);
    }

    public Iterable<Item> getAllItems() {
        return itemRepository.findAll();
    }
}
