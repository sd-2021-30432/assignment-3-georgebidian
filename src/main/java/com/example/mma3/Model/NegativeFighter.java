package com.example.mma3.Model;

public class NegativeFighter extends FighterDecorator{
    public NegativeFighter(IFighter fighter) {
        super(fighter);

        System.out.println("Adding Fighter normal");

        System.out.println("Negative quarantine fighter");
    }

    @Override
    public void changeColor(String color){
        super.changeColor("green");
    }

}
