package com.example.mma3.Strategy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MonthlyStrategy implements Strategy{
    @Override
    public String computeDate(String currentDate) {
        LocalDate date = LocalDate.parse(currentDate).plusMonths(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formatter);
    }
}
