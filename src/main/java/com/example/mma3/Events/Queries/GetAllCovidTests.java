package com.example.mma3.Events.Queries;

import com.example.mma3.Model.CovidTest;
import lombok.Data;

import java.util.List;

@Data
public class GetAllCovidTests {
    List<CovidTest> covidTests;
}
