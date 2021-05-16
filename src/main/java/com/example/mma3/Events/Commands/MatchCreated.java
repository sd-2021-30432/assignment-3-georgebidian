package com.example.mma3.Events.Commands;

import com.example.mma3.Model.Tournament;
import lombok.Data;

@Data
public class MatchCreated {

    private Tournament tournament;
    private String currentDate;
}
