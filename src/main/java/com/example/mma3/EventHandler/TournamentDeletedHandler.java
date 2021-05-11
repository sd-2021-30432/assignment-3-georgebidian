package com.example.mma3.EventHandler;

import com.example.mma3.Events.Commands.TournamentDeleted;
import com.example.mma3.Service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TournamentDeletedHandler implements IEventHandler<TournamentDeleted>{
    private final TournamentService tournamentService;

    @Override
    public ResponseEntity handle(TournamentDeleted event) {
        try{
            tournamentService.deleteById(event.getIdTournament());
        }
        catch(DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Tournament with id:" + event.getIdTournament() + " is currently used for a match");
        }
        catch(Exception e){
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
