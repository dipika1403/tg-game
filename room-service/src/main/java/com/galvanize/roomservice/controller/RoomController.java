package com.galvanize.roomservice.controller;

import com.galvanize.roomservice.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.galvanize.tggame.entity.RoomEntity;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomController.class);

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/move/{charId}/to/{roomId}")
    public void moveHeroToRoomById(@PathVariable Long charId,
                                   @PathVariable Long roomId) {
        roomService.moveHeroToRoomById(charId, roomId);
    }

    @GetMapping("/get/{id}")
    public RoomEntity getRoomById(@PathVariable Long id){
        return roomService.getRoomEntityById(id);
    }

    @PostMapping("/create")
    public RoomEntity createRoom(@RequestBody RoomEntity roomEntity) {
        return roomService.createRoom(roomEntity);
    }

    @PatchMapping("/exits/{id}")
    public RoomEntity updateExitsRoomById(@RequestBody List<Long> exits,
                                          @PathVariable Long id) {
        return roomService.updateExitsRoomById(exits, id);
    }

}
