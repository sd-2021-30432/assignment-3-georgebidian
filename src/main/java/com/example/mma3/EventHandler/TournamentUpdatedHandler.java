package com.example.mma3.EventHandler;

import com.example.mma3.Events.Commands.TournamentUpdated;
import com.example.mma3.Model.Tournament;
import com.example.mma3.Service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TournamentUpdatedHandler implements IEventHandler<TournamentUpdated>{
    private final TournamentService tournamentService;

    @Override
    public ResponseEntity handle(TournamentUpdated event) {
        Tournament tournamentSaved;
        Tournament tournament = event.getTournament();
        if(event.getIdTournament() == -1){
            tournamentSaved = tournamentService.save(tournament);
        }
        else{
            Tournament t = tournamentService.findById(event.getIdTournament());
            t.setIdTournament(tournament.getIdTournament());
            t.setTitle(tournament.getTitle());
            t.setLocation(tournament.getLocation());
            t.setDateTimeStart(tournament.getDateTimeStart());
            t.setType(tournament.getType());
            tournamentSaved = tournamentService.save(t);
        }
        return new ResponseEntity<Tournament>(tournamentSaved, HttpStatus.OK);
    }
}
