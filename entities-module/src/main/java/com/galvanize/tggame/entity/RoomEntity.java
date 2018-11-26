package com.galvanize.tggame.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomEntity {

    private Long id;
    private String name;
    private String description;

    private Set<Long> exits;

    @Builder
    public RoomEntity(Long id, String name, String description, Set<Long> exits) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.exits = exits;
    }
}
