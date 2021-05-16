package com.example.mma3.Model.DTO;

import com.example.mma3.Model.Fighter;
import com.example.mma3.Model.IFighter;
import com.example.mma3.Model.NegativeFighter;
import com.example.mma3.Model.PositiveFighter;
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
        String color = null;
        if(fighter.isInQuarantine()){
            IFighter fighterRed = new PositiveFighter(new Fighter());
            color = fighterRed.getColor();
        }
        else if(fighter.getCountNegatives() >= 2){
            IFighter fighterGreen = new NegativeFighter(new Fighter());
            color = fighterGreen.getColor();
        }
        else {
            color = fighter.getColor();
        }

        return FighterDTO.builder()
                .name(fighter.getName())
                .weight(fighter.getWeight())
                .color(color)
                .build();
    }
}
