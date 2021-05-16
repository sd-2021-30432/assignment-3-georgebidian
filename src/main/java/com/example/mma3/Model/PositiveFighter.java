package com.example.mma3.Model;

public class PositiveFighter extends FighterDecorator{
    public PositiveFighter(IFighter fighter) {
        super(fighter);
    }

    @Override
    public String getColor(){
        return "lightpink";
    }

}
