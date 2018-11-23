package com.galvanize.itemservice.service;

import com.galvanize.itemservice.entity.Item;
import com.galvanize.itemservice.exception.ObjectNotFoundException;
import com.galvanize.itemservice.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
public class ItemServiceTest {

    @MockBean
    ItemRepository itemRepository;
    @Autowired
    ItemService itemService;

    @Test
    public void saveItemTest() {
        Item mockItem = Item.builder().build();
        when(itemRepository.save(mockItem)).thenReturn(mockItem);
        itemService.saveItem(mockItem);
        verify(itemRepository, times(1)).save(mockItem);
    }

    @Test
    public void deleteItemByCorrectIdTest() {
        doNothing().when(itemRepository).deleteById(anyLong());
        when(itemRepository.existsById(anyLong())).thenReturn(true);
        itemService.deleteItemById(anyLong());
        verify(itemRepository, times(1)).existsById(anyLong());
        verify(itemRepository, times(1)).deleteById(anyLong());
    }

    @Test(expected = ObjectNotFoundException.class)
    public void deleteItemByWrongIdTest() {
        doNothing().when(itemRepository).deleteById(anyLong());
        when(itemRepository.existsById(anyLong())).thenReturn(false);
        itemService.deleteItemById(anyLong());
        verify(itemRepository, times(1)).existsById(anyLong());
        verify(itemRepository, times(0)).deleteById(anyLong());
    }

    @Test
    public void getItemByCorrectIdTest() {
        Item mockItem = Item.builder().id(42L).name("test").build();
        when(itemRepository.findById(anyLong())).thenReturn(Optional.of(mockItem));
        Item itemById = itemService.getItemById(anyLong());
        verify(itemRepository, times(1)).findById(anyLong());
        assertThat("Item was found", itemById, is(notNullValue()));
    }

    @Test(expected = ObjectNotFoundException.class)
    public void getItemByWrongIdTest() {
        when(itemRepository.findById(anyLong())).thenThrow(ObjectNotFoundException.class);
        itemService.getItemById(anyLong());
        verify(itemRepository, times(1)).findById(anyLong());
    }

    @Test
    public void getItemsByNameTest() {
        String mockName = "test";
        when(itemRepository.findByName(mockName)).thenReturn(new ArrayList<>());
        Iterable<Item> itemsByName = itemService.getItemsByName(mockName);
        verify(itemRepository, times(1)).findByName(mockName);
        assertThat(itemsByName, is(notNullValue()));
    }

    @Test
    public void getAllItemsTest() {
        when(itemRepository.findAll()).thenReturn(new ArrayList<>());
        itemService.getAllItems();
        verify(itemRepository, times(1)).findAll();
    }

}
