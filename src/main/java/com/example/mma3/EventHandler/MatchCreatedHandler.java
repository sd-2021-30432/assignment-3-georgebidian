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

    private List<Fighter> testFighters(String dateTimeStart){
        List<Fighter> fighters = fighterService.findAll();
        LocalDate date = LocalDate.parse(dateTimeStart);

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
            }
            else{
                fighter.setSecondTestId(covidTest.getIdCovidTest());
            }
        }

        for(Fighter fighter : fighters){
            if(fighter.isInQuarantine()){
                int healthyWeeks = 0;
                while(healthyWeeks < 3){
                    boolean result = generateCovidResult(10);
                    date = date.plusWeeks(1);
                    if(result == false){
                        healthyWeeks++;
                    }
                    else{
                        healthyWeeks = 0;
                    }
                }
                CovidTest newCovidTest = new CovidTest();
                newCovidTest.setTestDate(date.toString());
                newCovidTest.setResult(false);
                CovidTest covidTest =  covidTestService.save(newCovidTest);
                fighter.setSecondTestId(covidTest.getIdCovidTest());
                fighter.setInQuarantine(false);
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

    private List<Fighter> pickFighters(List<Fighter> fighterList, String dateTimeStart, int weekFlag) throws Exception {
        List<Fighter> pickedFighters = new ArrayList<Fighter>();
        for(Fighter fighter : fighterList){
            CovidTest secondTest = covidTestService.findById(fighter.getSecondTestId());
            LocalDate secondTestDate = LocalDate.parse(secondTest.getTestDate());
            LocalDate fightDate = LocalDate.parse(dateTimeStart);
            if(!isScheduled(fighter) &&  fightDate.isAfter(secondTestDate)){
                pickedFighters.add(fighter);
            }
        }

        if(pickedFighters.isEmpty()){
            throw new Exception("No fighter found for week " + weekFlag);
        }

        fighterList.remove(pickedFighters.get(0));
        for(Fighter fighter : fighterList){
            CovidTest secondTest = covidTestService.findById(fighter.getSecondTestId());
            LocalDate secondTestDate = LocalDate.parse(secondTest.getTestDate());
            LocalDate fightDate = LocalDate.parse(dateTimeStart);
            float weightDiff = Math.abs(pickedFighters.get(0).getWeight() - fighter.getWeight());
            if(!isScheduled(fighter) &&  fightDate.isAfter(secondTestDate) && weightDiff <= 5){
                pickedFighters.add(fighter);
            }
        }

        if(pickedFighters.size() == 1){
            throw new Exception("Can not find second fighter for week " + weekFlag);
        }

        return pickedFighters;
    }


    @Override
    public ResponseEntity handle(MatchCreated event) {
        int weekFlag = event.getWeekFlag();
        Tournament tournament = event.getTournament();
        MatchBuilder builder = new MatchBuilder();
        String dateTimeStart = tournament.getDateTimeStart();
        List<Fighter> fighters = testFighters(dateTimeStart);
        LocalDate currentDate = LocalDate.parse(dateTimeStart).plusDays( (int)(weekFlag - 1) * 7 + (int)(Math.random() * 6) );
        List<Fighter> pickedFighters;
        try {
            pickedFighters = pickFighters(fighters, currentDate.toString(), (int)weekFlag);
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
