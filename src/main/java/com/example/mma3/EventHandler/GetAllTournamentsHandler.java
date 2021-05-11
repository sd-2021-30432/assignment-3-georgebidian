package com.example.mma3.EventHandler;

import com.example.mma3.Events.Queries.GetAllMatches;
import com.example.mma3.Events.Queries.GetAllTournaments;
import com.example.mma3.Service.MatchService;
import com.example.mma3.Service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetAllTournamentsHandler implements IEventHandler<GetAllTournaments> {
    private final TournamentService tournamentService;

    @Override
    public ResponseEntity handle(GetAllTournaments event) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(tournamentService.findAll());
    }
}
