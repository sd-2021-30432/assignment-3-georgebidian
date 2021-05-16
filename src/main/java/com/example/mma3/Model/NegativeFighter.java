package com.example.mma3.Model;

public class NegativeFighter extends FighterDecorator{
    public NegativeFighter(IFighter fighter) {
        super(fighter);
    }

    @Override
    public String getColor(){
        return "lightgreen";
    }

}
