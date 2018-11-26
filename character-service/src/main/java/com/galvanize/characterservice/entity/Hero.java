package com.galvanize.characterservice.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.galvanize.tggame.entity.ClassCharacter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "hero")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Hero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private Long location;
    private int hitPoints;

    @Builder
    public Hero(Long id, String name, Long location,
                ClassCharacter classCharacter, int heroInt, int heroWis,
                int heroCha, int heroStr, int heroDex, int heroCon,
                int hitPoints) {
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
        this.hitPoints = hitPoints;
    }
}