package com.example.mma3.EventHandler;

import com.example.mma3.Events.Commands.MatchUpdated;
import com.example.mma3.Model.MatchT;
import com.example.mma3.Service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchUpdatedHandler implements IEventHandler<MatchUpdated>{
    private final MatchService matchService;

    @Override
    public ResponseEntity handle(MatchUpdated event) {
        MatchT matchSaved;
        MatchT match = event.getMatch();
        if(event.getIdMatchT() == -1){
            matchSaved = matchService.save(match);
        }
        else{
            MatchT m = matchService.findById(event.getIdMatchT());
            m.setIdMatchT(match.getIdMatchT());
            m.setIdFighter1(match.getIdFighter1());
            m.setIdFighter2(match.getIdFighter2());
            m.setIdTournament(match.getIdTournament());
            m.setWinner(match.getWinner());
            m.setRounds(match.getRounds());
            m.setDateTimeStart(match.getDateTimeStart());
            matchSaved = matchService.save(m);
        }
        return new ResponseEntity<MatchT>(matchSaved, HttpStatus.OK);
    }
}
