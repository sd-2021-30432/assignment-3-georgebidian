package com.example.mma3.Model;

public class PositiveFighter extends FighterDecorator{
    public PositiveFighter(IFighter fighter) {
        super(fighter);

        System.out.println("Adding Fighter normal");

        System.out.println("Positive quarantine fighter");
    }

    @Override
    public void changeColor(String color){
        super.changeColor("red");
    }

}
