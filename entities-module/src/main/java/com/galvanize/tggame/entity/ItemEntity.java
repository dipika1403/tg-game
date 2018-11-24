package com.galvanize.tggame.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemEntity {

    private Long id;
    private String name;

    @Builder
    public ItemEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
