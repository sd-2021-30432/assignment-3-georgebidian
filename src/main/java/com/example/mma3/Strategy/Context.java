package com.example.mma3.Strategy;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Context {
    private Strategy strategy;

    public int executeStrategy(int currentWeek){
        return strategy.addWeeks(currentWeek);
    }
}
