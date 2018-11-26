package com.galvanize.roomservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.roomservice.exception.HeroIsDeadException;
import com.galvanize.roomservice.exception.MoveForbiddenException;
import com.galvanize.roomservice.service.RoomService;
import com.galvanize.tggame.entity.RoomEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RoomController.class, secure = false)
public class RoomControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomControllerTest.class);

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RoomService roomService;
    @MockBean
    RabbitTemplate rabbitTemplate;

    @Test
    public void moveHeroToRoomByRoomIdWrongWay() throws Exception {
        doThrow(MoveForbiddenException.class).when(roomService).moveHeroToRoomById(anyLong(), anyLong());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/room/move/{charId}/to/{roomId}", anyLong(), anyLong()))
                .andExpect(status().isForbidden())
                .andDo(print());

        verify(roomService, Mockito.times(1)).moveHeroToRoomById(anyLong(), anyLong());
    }

    @Test
    public void moveHeroToRoomByRoomIdHeroIsDead() throws Exception {
        doThrow(HeroIsDeadException.class).when(roomService).moveHeroToRoomById(anyLong(), anyLong());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/room/move/{charId}/to/{roomId}", anyLong(), anyLong()))
                .andExpect(status().isUnauthorized())
                .andDo(print());

        verify(roomService, Mockito.times(1)).moveHeroToRoomById(anyLong(), anyLong());
    }

    @Test
    public void moveHeroToRoomByRoomId() throws Exception {
        doNothing().when(roomService).moveHeroToRoomById(anyLong(), anyLong());

        mockMvc.perform(MockMvcRequestBuilders
                .post("/room/move/{charId}/to/{roomId}", anyLong(), anyLong()))
                .andExpect(status().isOk())
                .andDo(print());

        verify(roomService, Mockito.times(1)).moveHeroToRoomById(anyLong(), anyLong());
    }

    @Test
    public void getRoomByIdTest() throws Exception {
        RoomEntity roomEntity = RoomEntity.builder().build();
        when(roomService.getRoomEntityById(anyLong())).thenReturn(roomEntity);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/room/get/{id}", anyLong())
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andReturn();

        verify(roomService, Mockito.times(1)).getRoomEntityById(anyLong());
    }

    @Test
    public void createRoom() throws Exception {
        RoomEntity roomEntity = RoomEntity.builder().build();
        when(roomService.createRoom(roomEntity)).thenReturn(roomEntity);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/room/create")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsBytes(roomEntity))
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andReturn();

        verify(roomService, times(1)).createRoom(roomEntity);
    }

    @Test
    public void updateExitsRoomByIdTest() throws Exception {
        List<Long> mockExits = new ArrayList<>();
        Long mockRoomId = 42L;
        RoomEntity roomEntity = RoomEntity.builder().build();

        when(roomService.updateExitsRoomById(mockExits, mockRoomId)).thenReturn(roomEntity);

        mockMvc.perform(MockMvcRequestBuilders
                .patch("/room/exits/{id}", mockRoomId)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsBytes(mockExits))
                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andReturn();

        verify(roomService, Mockito.times(1)).updateExitsRoomById(mockExits, mockRoomId);
    }
}