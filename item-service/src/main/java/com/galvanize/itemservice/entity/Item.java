package com.galvanize.itemservice.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Item {

    private Long id;
    private String name;

    @Builder
    public Item(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
