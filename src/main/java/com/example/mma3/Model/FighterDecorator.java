package com.example.mma3.Model;

abstract class FighterDecorator implements IFighter{
    protected IFighter fighter;

    public FighterDecorator(IFighter fighter){
        this.fighter = fighter;
    }

    @Override
    public void changeColor(String color) {
        this.fighter.changeColor(color);
    }
}
