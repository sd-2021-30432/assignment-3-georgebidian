package com.example.mma3.Strategy;

public class WeeklyStrategy implements Strategy{
    @Override
    public int addWeeks(int currentWeek){
        return currentWeek + 1;
    }
}
