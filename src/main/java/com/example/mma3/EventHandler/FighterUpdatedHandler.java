package com.example.mma3.EventHandler;

import com.example.mma3.Events.Commands.FighterUpdated;
import com.example.mma3.Model.Fighter;
import com.example.mma3.Service.FighterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FighterUpdatedHandler implements IEventHandler<FighterUpdated>{
    private final FighterService fighterService;

    @Override
    public ResponseEntity handle(FighterUpdated event) {
        Fighter fighterSaved;
        Fighter fighter = event.getFighter();
        if(event.getIdFighter() == -1){
            fighterSaved = fighterService.save(fighter);
        }
        else{
            Fighter f = fighterService.findById(event.getIdFighter());
            f.setFirstname(fighter.getFirstname());
            f.setLastname(fighter.getLastname());
            f.setWeight(fighter.getWeight());
            f.setInQuarantine(fighter.isInQuarantine());
            f.setInitialTestId(fighter.getInitialTestId());
            f.setSecondTestId(fighter.getSecondTestId());
            f.setCountNegatives(fighter.getCountNegatives());
            fighterSaved = fighterService.save(f);
        }
        return new ResponseEntity<Fighter>(fighterSaved, HttpStatus.OK);
    }
}
