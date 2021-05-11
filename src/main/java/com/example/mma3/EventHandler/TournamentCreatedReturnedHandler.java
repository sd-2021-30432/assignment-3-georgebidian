package com.example.mma3.EventHandler;

import com.example.mma3.Events.Commands.TournamentCreatedReturned;
import com.example.mma3.Model.Tournament;
import com.example.mma3.Service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TournamentCreatedReturnedHandler implements IEventHandler<TournamentCreatedReturned> {
    private final TournamentService tournamentService;

    @Override
    public ResponseEntity handle(TournamentCreatedReturned event) {
        Tournament createdTournament = tournamentService.save(event.getTournament());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(createdTournament);
    }
}