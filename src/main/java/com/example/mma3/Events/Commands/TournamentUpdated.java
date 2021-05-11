package com.example.mma3.Events.Commands;

import com.example.mma3.Model.Tournament;
import lombok.Data;

@Data
public class TournamentUpdated {
    private int idTournament;
    private Tournament tournament;
}
