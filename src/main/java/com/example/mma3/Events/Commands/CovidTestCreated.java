package com.example.mma3.Events.Commands;

import com.example.mma3.Model.CovidTest;
import lombok.Data;

@Data
public class CovidTestCreated {
    private CovidTest covidTest;
}
