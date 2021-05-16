package com.example.mma3.Model.DTO;

import com.example.mma3.Model.Fighter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class FighterDTO {
    private String name;
    private float weight;
    private String color;

    public static FighterDTO fromFighter(Fighter fighter){
        return FighterDTO.builder()
                .name(fighter.getName())
                .weight(fighter.getWeight())
                .color(fighter.getColor())
                .build();
    }
}
