package com.example.mma3.Events.Queries;

import com.example.mma3.Model.Tournament;
import lombok.Data;

import java.util.List;

@Data
public class GetAllTournaments {
    List<Tournament> tournaments;
}
