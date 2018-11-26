package com.galvanize.roomservice.service;

import com.galvanize.roomservice.MessageSender;
import com.galvanize.roomservice.RoomServiceConfigReader;
import com.galvanize.roomservice.entity.Room;
import com.galvanize.roomservice.exception.HeroIsDeadException;
import com.galvanize.roomservice.exception.MoveForbiddenException;
import com.galvanize.roomservice.exception.RoomNotFoundException;
import com.galvanize.roomservice.repository.RoomRepository;
import com.galvanize.tggame.entity.HeroEntity;
import com.galvanize.tggame.entity.HeroMoveTo;
import com.galvanize.tggame.entity.RoomEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomService.class);

//    private final RabbitTemplate rabbitTemplate;
    private final RoomRepository roomRepository;
    private final MessageSender messageSender;

    private final RoomServiceConfigReader roomServiceConfigReader;



    @Autowired
    public RoomService(RoomRepository roomRepository, MessageSender messageSender, RoomServiceConfigReader roomServiceConfigReader) {
//    public RoomService(RoomRepository roomRepository, MessageSender messageSender, RabbitTemplate rabbitTemplate, RoomServiceConfigReader roomServiceConfigReader) {
        this.roomRepository = roomRepository;
        this.messageSender = messageSender;
//        this.rabbitTemplate = rabbitTemplate;
        this.roomServiceConfigReader = roomServiceConfigReader;
    }

    public void moveHeroToRoomById(Long charId, Long roomId) {
        HeroEntity heroEntityById = getHeroEntityById(charId);
        if (heroEntityById.getHitPoints() < 1)
            throw new HeroIsDeadException("Illegal move with dead hero");
        RoomEntity location = heroEntityById.getLocation();
        if ((location != null)) {
            Room roomById = getRoomById(location.getId());
            Set<Long> exits = roomById.getExits().stream()
                    .map(Room::getId).collect(Collectors.toCollection(HashSet::new));
            if (!exits.contains(roomId)) {
                throw new MoveForbiddenException(String.format("Illegal move to room id: %d", roomId));
            }
        }
        HeroMoveTo heroMoveTo = HeroMoveTo.builder().heroId(charId).roomId(roomId).build();
        String exchange = roomServiceConfigReader.getAppExchangeName();
        String routingKey = roomServiceConfigReader.getAppRoutingKey();
//        messageSender.sendMessage(rabbitTemplate, exchange, routingKey, heroMoveTo);
        messageSender.sendMessage(exchange, routingKey, heroMoveTo);
    }

    private Room getRoomById(Long id) {
        Optional<Room> optRoom = roomRepository.findById(id);
        if (!optRoom.isPresent()) {
            throw new RoomNotFoundException(String.format("room was not found by id: %d", id));
        }
        return optRoom.get();
    }

    public RoomEntity getRoomEntityById(Long id) {
        return adaptRoom(getRoomById(id));
    }

    public RoomEntity createRoom(RoomEntity roomEntity) {
        Room.RoomBuilder roomBuilder = Room.builder()
                .name(roomEntity.getName())
                .description(roomEntity.getDescription());
        Set<Room> exits = new HashSet<>();
        roomEntity.getExits().forEach(e -> exits.add(getRoomById(e)));
        Room room = roomBuilder.exits(exits).build();
        Room savedRoom = roomRepository.save(room);

        return adaptRoom(savedRoom);
    }

    private RoomEntity adaptRoom(Room room) {
        Set<Long> exits = new HashSet<>();
        room.getExits().forEach(e -> exits.add(e.getId()));
        return RoomEntity.builder()
                .id(room.getId())
                .name(room.getName())
                .description(room.getDescription())
                .exits(exits)
                .build();
    }

    private HeroEntity getHeroEntityById(Long id) {
        HeroEntity heroEntity = null;
        if (id != null) {

            String REST_URI
                    = "http://localhost:8182/character/get/";

            Client client = ClientBuilder.newClient();

            try {
                heroEntity = client
                        .target(REST_URI)
                        .path(String.valueOf(id))
                        .request(MediaType.APPLICATION_JSON)
                        .get(HeroEntity.class);
            } catch (Exception e) {
                LOGGER.error("Location not found by id: {}", id);
            }
        }
        return heroEntity;
    }

    public RoomEntity updateExitsRoomById(List<Long> exits, Long id) {
        Room roomById = getRoomById(id);
        Set<Room> exitsRoom = new HashSet<>();
        exits.forEach(e -> exitsRoom.add(getRoomById(e)));
        roomById.setExits(exitsRoom);
        return adaptRoom(roomRepository.save(roomById));
    }
}
