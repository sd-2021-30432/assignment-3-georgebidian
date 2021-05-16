package com.example.mma3.EventHandler;

import com.example.mma3.Events.Commands.MatchCreated;
import com.example.mma3.Model.*;
import com.example.mma3.Service.*;
import com.example.mma3.Strategy.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class MatchCreatedHandler implements IEventHandler<MatchCreated> {

    private final CovidTestService covidTestService;
    private final MatchService matchService;
    private final FighterService fighterService;

    public static boolean generateCovidResult(int probability){
        boolean result = new Random().nextInt(probability)==0;
        return result;
    }

    private List<Fighter> testFighters(String currentDate){
        List<Fighter> fighters = fighterService.findAll();
        LocalDate date = LocalDate.parse(currentDate);

        for(Fighter fighter : fighters){
            if(fighter.isInQuarantine()){
                boolean result = generateCovidResult(10);
                date = date.plusWeeks(1);
                CovidTest newCovidTest = new CovidTest();
                newCovidTest.setTestDate(date.toString());
                if(result == false){
                    newCovidTest.setResult(false);
                    CovidTest covidTest =  covidTestService.save(newCovidTest);
                    fighter.setSecondTestId(covidTest.getIdCovidTest());
                    fighter.incrementNegatives();
                }
                else{
                    newCovidTest.setResult(true);
                    CovidTest covidTest =  covidTestService.save(newCovidTest);
                    fighter.setInitialTestId(covidTest.getIdCovidTest());
                    fighter.setCountNegatives(0);
                }
                if(fighter.getCountNegatives() == 3){
                    fighter.setInQuarantine(false);
                }
                fighterService.save(fighter);
            }
        }

        return fighters;
    }

    private boolean isScheduled(Fighter fighter){
        List<MatchT> matches = matchService.findAll();
        for(MatchT m : matches){
            if(fighter.getIdFighter() == m.getIdFighter1() || fighter.getIdFighter() == m.getIdFighter2())
                return true;
        }
        return false;
    }

    private List<Fighter> pickFighters(List<Fighter> fighterList, String currentDate) throws Exception {
        List<Fighter> pickedFighters = new ArrayList<Fighter>();
        for(Fighter fighter : fighterList){
            if(!isScheduled(fighter) &&  !fighter.isInQuarantine()){
                pickedFighters.add(fighter);
            }
        }

        if(pickedFighters.isEmpty()){
            throw new Exception("No fighter found for date " + currentDate);
        }

        fighterList.remove(pickedFighters.get(0));
        for(Fighter fighter : fighterList){
            float weightDiff = Math.abs(pickedFighters.get(0).getWeight() - fighter.getWeight());
            if(!isScheduled(fighter) &&  !fighter.isInQuarantine() && weightDiff <= 5){
                pickedFighters.add(fighter);
            }
        }

        if(pickedFighters.size() == 1){
            throw new Exception("Can not find second fighter for date " + currentDate);
        }

        return pickedFighters;
    }


    @Override
    public ResponseEntity handle(MatchCreated event) {
        Tournament tournament = event.getTournament();
        MatchBuilder builder = new MatchBuilder();
        List<Fighter> fighters = null;
        //In case the type of tournament is Monthly, test the fighters 4 times
        //otherwise test the fighters only once
        if(tournament.getType().equals("Monthly")){
            for(int i = 0; i < 4; i++)
                fighters = testFighters(event.getCurrentDate());
        }
        else{
            fighters = testFighters(event.getCurrentDate());
        }
        LocalDate currentDate = LocalDate.parse(event.getCurrentDate());
        List<Fighter> pickedFighters;
        try {
            pickedFighters = pickFighters(fighters, event.getCurrentDate());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
        int idFighter1 = pickedFighters.get(0).getIdFighter();
        int idFighter2 = pickedFighters.get(1).getIdFighter();
        builder.addIdTournament(tournament.getIdTournament())
                .addDateTimeStart(currentDate.toString())
                .addIdFighter1(idFighter1)
                .addIdFighter2(idFighter2)
                .addRounds()
                .addWinner();
        MatchT match = builder.build();
        MatchT createdMatch = matchService.save(match);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idMatchT}").buildAndExpand(createdMatch.getIdMatchT()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
