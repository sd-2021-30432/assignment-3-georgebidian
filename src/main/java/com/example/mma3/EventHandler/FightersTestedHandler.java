package com.example.mma3.EventHandler;

import com.example.mma3.Events.Commands.FightersTested;
import com.example.mma3.Model.CovidTest;
import com.example.mma3.Model.Fighter;
import com.example.mma3.Service.CovidTestService;
import com.example.mma3.Service.FighterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class FightersTestedHandler implements IEventHandler<FightersTested> {

    private final FighterService fighterService;
    private final CovidTestService covidTestService;

    public static boolean generateCovidResult(int probability){
        boolean result = new Random().nextInt(probability)==0;
        return result;
    }

    @Override
    public ResponseEntity handle(FightersTested event) {
        List<Fighter> fighters = fighterService.findAll();
        LocalDate date = LocalDate.parse(event.getDateTimeStart());

        //Setting initial values
        for(Fighter fighter : fighters){
            CovidTest newCovidTest = new CovidTest();
            boolean result = generateCovidResult(10);
            newCovidTest.setResult(result);
            newCovidTest.setTestDate(date.toString());
            CovidTest covidTest =  covidTestService.save(newCovidTest);
            if(result == true){
                fighter.setInitialTestId(covidTest.getIdCovidTest());
                fighter.setInQuarantine(true);
                fighter.setCountNegatives(0);
            }
            else{
                fighter.incrementNegatives();
                fighter.setSecondTestId(covidTest.getIdCovidTest());
            }
            fighterService.save(fighter);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(fighters);
    }
}
