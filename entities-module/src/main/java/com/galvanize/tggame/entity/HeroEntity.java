package com.galvanize.tggame.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HeroEntity {

    private Long id;
    private String name;
    private ClassCharacter classCharacter;
    @JsonProperty(value = "int")
    private int heroInt;
    @JsonProperty(value = "wis")
    private int heroWis;
    @JsonProperty(value = "cha")
    private int heroCha;
    @JsonProperty(value = "str")
    private int heroStr;
    @JsonProperty(value = "dex")
    private int heroDex;
    @JsonProperty(value = "con")
    private int heroCon;
    private RoomEntity location;
    private List<ItemEntity> inventory;
    private int hitPoints;

    @Builder
    public HeroEntity(Long id, String name, RoomEntity location,
                      ClassCharacter classCharacter, int heroInt, int heroWis,
                      int heroCha, int heroStr, int heroDex, int heroCon,
                      List<ItemEntity> inventory, int hitPoints) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.classCharacter = classCharacter;
        this.heroInt = heroInt;
        this.heroWis = heroWis;
        this.heroCha = heroCha;
        this.heroStr = heroStr;
        this.heroDex = heroDex;
        this.heroCon = heroCon;
        this.inventory = inventory;
        this.hitPoints = hitPoints;
    }
}
