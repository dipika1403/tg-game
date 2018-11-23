package com.galvanize.itemservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.itemservice.entity.Item;
import com.galvanize.itemservice.exception.ObjectNotFoundException;
import com.galvanize.itemservice.service.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ItemController.class, secure = false)
public class ItemControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemControllerTest.class);
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService service;

    /*
    Scenario: Create where object not in data store.
    Given An object of type  "item" with the format { "id": 1, "name": "sword" }, that is not in the data store.
    When a POST call is made to /object/create/{className}
    Then The object is registered in the data store and a 200 is returned.
    All objects have an "id" field that holds their key value.
    */
    @Test
    public void createItemTest() throws Exception {
        Item item = Item.builder().name("sword").build();
        Item savedItem = Item.builder().id(1L).name("sword").build();

        when(service.saveItem(item)).thenReturn(savedItem);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/object/create/{className}", "sword")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsBytes(new Item()))
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andReturn();
        verify(service, times(1)).saveItem(item);
    }


    /*
    Scenario: Create where object is in data store.
    Given An object of type  "item" with the format { "id": 1, "name": "sword" }, that is in the data store.
    When a POST call is made to /object/create/{className}
    Then The object that has type=="item" and "id"==1 will be overwritten in the data store and a 200 is returned.
    All objects have an "id" field that holds their key value.
    */
    @Test
    public void updateItemTest() throws Exception {
        Item item = Item.builder().id(1L).name("sword").build();
        Item updatedItem = Item.builder().id(1L).name("double hand sword").build();

        when(service.saveItem(item)).thenReturn(updatedItem);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/object/create/{className}", "sword")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsBytes(item))
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andReturn();
        verify(service, times(1)).saveItem(item);
    }



    /*
    Scenario: Delete item that IS in the data store.
    Given An object of type  "item" with the format { "id": 1, "name": "sword" }
    When a POST call is made to /object/delete/{id}
    Then The object is deleted from the data store and a 200 is returned.
    */
    @Test
    public void deleteItemByCorrectIdTest() throws Exception {
        doNothing().when(service).deleteItemById(anyLong());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/object/delete/{id}", anyLong()))
                .andExpect(status().isOk())
                .andDo(print());

        verify(service, times(1)).deleteItemById(anyLong());
    }


    /*
    Scenario: Delete item that is NOT in the data store.
    Given An object of type  "item" with the format { "id": 1, "name": "sword" }
    When a POST call is made to /object/delete/{id}
    Then The object is not found and the service returns 404.
    */
    @Test
    public void deleteItemByWrongIdTest() throws Exception {
        doThrow(ObjectNotFoundException.class).when(service).deleteItemById(anyLong());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/object/delete/{id}", anyLong()))
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(service, times(1)).deleteItemById(anyLong());

    }

    /*
    Scenario: Get a specific item.
    Given An object of type  "item" with the format { "id": 1, "name": "sword" } is in the data store.
    When a GET call is made to /object/get/{id}
    Then The object is returned in json format along with a 200 code.
    */
    @Test
    public void getItemWithCorrectIdTest() throws Exception {
        Item mocItem = Item.builder().id(42L).name("test").build();
        when(service.getItemById(anyLong())).thenReturn(mocItem);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/object/get/{id}", anyLong())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andReturn();

        verify(service, Mockito.times(1)).getItemById(anyLong());

    }

    /*
    Scenario: Get a specific item but it is not in the data store.
    Given An object of type  "item" with the format { "id": 1, "name": "sword" } is not in the data store.
    When a GET call is made to /object/get/{id}
    Then The service returns 404.
    */
    @Test
    public void getItemWithWrongIdTest() throws Exception {
        when(service.getItemById(anyLong())).thenThrow(ObjectNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/object/get/{id}", anyLong())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn();

        verify(service, Mockito.times(1)).getItemById(anyLong());
    }

    /*
    Scenario: Get all items of a specific type.
    Given Several objects of type  "item" are in the data store.
    When a GET call is made to /object/get/className/{className}
    Then The service returns a JSON array of all the items that match the className (could be an empty array) and a 200.
    */
    @Test
    public void getItemsByNameTest() throws Exception {
        List<Item> mockItems = new ArrayList<>(Arrays.asList(
                Item.builder().id(42L).name("test").build(),
                Item.builder().id(43L).name("test").build()
        ));

        String mockClassName = "test";

        when(service.getItemsByName(mockClassName)).thenReturn(mockItems);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/object/get/className/{className}", mockClassName)
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andReturn();

        verify(service, Mockito.times(1)).getItemsByName(anyString());
    }

    /*
    Scenario: Get all items in the registry.
    Given Several objects are in the data store.
    When a GET call is made to /object/get/
    Then The service returns a JSON array of all the items in the data store(could be an empty array) and a 200.
    */
    @Test
    public void getAllItemTest() throws Exception {
        List<Item> mockItems = new ArrayList<>(Arrays.asList(
                Item.builder().id(42L).name("test").build(),
                Item.builder().id(43L).name("test").build()
        ));

        when(service.getAllItems()).thenReturn(mockItems);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/object/get/")
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andReturn();

        verify(service, Mockito.times(1)).getAllItems();

    }

}