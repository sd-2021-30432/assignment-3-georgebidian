package com.example.mma3.EventHandler;

import com.example.mma3.Events.Commands.TournamentCreated;
import com.example.mma3.Model.Tournament;
import com.example.mma3.Service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class TournamentCreatedHandler implements IEventHandler<TournamentCreated> {
    private final TournamentService tournamentService;

    @Override
    public ResponseEntity handle(TournamentCreated event) {
        try{
            Tournament createdTournament = tournamentService.save(event.getTournament());
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idFighter}").buildAndExpand(createdTournament.getIdTournament()).toUri();
            return ResponseEntity.created(uri).build();
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }

    }
}
