package com.example.mma3.EventHandler;

import com.example.mma3.Events.Queries.GetTournamentById;
import com.example.mma3.Model.Tournament;
import com.example.mma3.Service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetTournamentByIdHandler implements IEventHandler<GetTournamentById> {
    private final TournamentService tournamentService;

    @Override
    public ResponseEntity handle(GetTournamentById event) {
        if(event.getIdTournament() != -1){
            Tournament tournament = tournamentService.findById(event.getIdTournament());
            if(tournament == null)
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Failed: No Tournament with ID=" + event.getIdTournament());
            else return ResponseEntity.status(HttpStatus.ACCEPTED).body(tournament);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Tournament());
    }
}
