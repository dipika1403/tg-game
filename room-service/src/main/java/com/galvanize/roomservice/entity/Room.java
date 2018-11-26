package com.galvanize.roomservice.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "room")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column
    private String description;

    @ElementCollection
    @CollectionTable(name="room_exit", joinColumns=@JoinColumn(name="room_id"))
    @Column(name="exit_id")
    @JsonProperty(value = "exits")
    private Set<Long> exitsLong = new HashSet<>();

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "room_exit",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "exit_id")
    )
    private Set<Room> exits;

    @Builder
    public Room(Long id, String name, String description, Set<Room> exits) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.exits = exits;
    }

}
