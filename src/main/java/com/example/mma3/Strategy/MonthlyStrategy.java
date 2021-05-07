package com.example.mma3.Strategy;

public class MonthlyStrategy implements Strategy{
    @Override
    public int addWeeks(int currentWeek){
        return currentWeek + 4;
    }
}
