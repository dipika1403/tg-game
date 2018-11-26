package com.galvanize.tggame.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HeroMoveTo {
    Long heroId;
    Long roomId;

    @Builder
    public HeroMoveTo(Long heroId, Long roomId) {
        this.heroId = heroId;
        this.roomId = roomId;
    }
}
