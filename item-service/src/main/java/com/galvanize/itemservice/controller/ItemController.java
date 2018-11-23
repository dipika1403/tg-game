package com.galvanize.itemservice.controller;

import com.galvanize.itemservice.entity.Item;
import com.galvanize.itemservice.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/object")
public class ItemController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/create/{className}")
    public Item saveItem(@RequestBody Item item,
                         @PathVariable String className) {
        LOGGER.info("Request body = {}", item);
        LOGGER.info("Path variable className = {}", className);
        if (item.getName() == null)
            item = Item.builder().name(className).build();
        return itemService.saveItem(item);
    }

    @PostMapping("/delete/{id}")
    public void deleteItemById(@PathVariable Long id) {
        LOGGER.info("delete Item by id: {}", id);
        itemService.deleteItemById(id);

    }


    @GetMapping("/get/{id}")
    public Item getItemById(@PathVariable Long id) {
        LOGGER.info("get Item by id: {}", id);
        return itemService.getItemById(id);
    }


    @GetMapping("/get/className/{className}")
    public Iterable<Item> getItemByClassName(@PathVariable String className) {
        LOGGER.info("get Items by className: {}", className);
        return itemService.getItemsByName(className);
    }


    @GetMapping("/get/")
    public Iterable<Item> getAllItems() {
        LOGGER.info("get all Items");
        return itemService.getAllItems();
    }
}
