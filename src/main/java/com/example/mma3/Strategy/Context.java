package com.example.mma3.Strategy;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Context {
    private Strategy strategy;

    public String executeStrategy(String currentDate){
        return strategy.computeDate(currentDate);
    }
}
