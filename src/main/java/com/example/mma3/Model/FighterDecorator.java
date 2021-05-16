package com.example.mma3.Model;

abstract class FighterDecorator implements IFighter{
    protected IFighter fighter;

    public FighterDecorator(IFighter fighter){
        this.fighter = fighter;
    }

    @Override
    public String getColor() {
        return this.fighter.getColor();
    }
}
