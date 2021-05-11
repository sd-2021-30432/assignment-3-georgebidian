package com.example.mma3.EventHandler;

import com.example.mma3.Events.Queries.GetAllCovidTests;
import com.example.mma3.Events.Queries.GetFighterById;
import com.example.mma3.Model.Fighter;
import com.example.mma3.Service.CovidTestService;
import com.example.mma3.Service.FighterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetFighterByIdHandler implements IEventHandler<GetFighterById> {
    private final FighterService fighterService;

    @Override
    public ResponseEntity handle(GetFighterById event) {
        if(event.getIdFighter() != -1){
            Fighter fighter = fighterService.findById(event.getIdFighter());
            if(fighter == null)
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Failed: No Fighter with ID=" + event.getIdFighter());
            else return ResponseEntity.status(HttpStatus.ACCEPTED).body(fighter);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Fighter());
    }
}
