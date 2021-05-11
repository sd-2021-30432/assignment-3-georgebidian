package com.example.mma3.Events.Commands;

import com.example.mma3.Model.MatchT;
import lombok.Data;

@Data
public class MatchUpdated {
    private MatchT match;
    private int idMatchT;
}
