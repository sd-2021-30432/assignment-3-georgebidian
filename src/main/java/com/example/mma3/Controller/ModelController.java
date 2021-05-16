package com.example.mma3.Controller;

import com.example.mma3.EventHandler.Mediator;
import com.example.mma3.Events.Commands.*;
import com.example.mma3.Events.Queries.*;
import com.example.mma3.Model.*;
import com.example.mma3.Strategy.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/mma/data")
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
public class ModelController {

    @Autowired
    private Mediator mediator;

    @GetMapping(path="/covidtests")
    public ResponseEntity getAllTests(){
        return mediator.handle(new GetAllCovidTests());
    }

    @GetMapping(path="/fighters")
    public ResponseEntity getAllFighters(){
        return mediator.handle(new GetAllFighters());
    }

    @GetMapping(path="/fightersdto")
    public ResponseEntity getAllFightersDTO()
    {
        return mediator.handle(new GetAllFightersDTO());
    }

    @GetMapping(path="/matches")
    public ResponseEntity getAllMatches(){
        return mediator.handle(new GetAllMatches());
    }

    @GetMapping(path="/tournaments")
    public ResponseEntity getAllTournaments(){
        return mediator.handle(new GetAllTournaments());
    }

    @DeleteMapping(path="/deleteFighter/{idFighter}")
    public ResponseEntity deleteFighter(@PathVariable int idFighter){
        FighterDeleted event = new FighterDeleted();
        event.setIdFighter(idFighter);
        return mediator.handle(event);
    }

    @DeleteMapping(path="/deleteCovidTest/{idCovidTest}")
    public ResponseEntity deleteCovidTest(@PathVariable int idCovidTest){
        CovidTestDeleted event = new CovidTestDeleted();
        event.setIdCovidTest(idCovidTest);
        return mediator.handle(event);
    }

    @DeleteMapping(path="/deleteTournament/{idTournament}")
    public ResponseEntity deleteTournament(@PathVariable int idTournament){
        TournamentDeleted event = new TournamentDeleted();
        event.setIdTournament(idTournament);
        return mediator.handle(event);
    }

    @DeleteMapping(path="/deleteMatch/{idMatchT}")
    public ResponseEntity<Void> deleteMatch(@PathVariable int idMatchT){
        MatchDeleted event = new MatchDeleted();
        event.setIdMatchT(idMatchT);
        return mediator.handle(event);
    }

    @GetMapping(path="/fighters/{idFighter}")
    public ResponseEntity getFighter(@PathVariable int idFighter){
        GetFighterById event = new GetFighterById();
        event.setIdFighter(idFighter);
        return mediator.handle(event);
    }

    @GetMapping(path="/covidtests/{idCovidTest}")
    public ResponseEntity getCovidTest(@PathVariable int idCovidTest){
        GetCovidTestById event = new GetCovidTestById();
        event.setIdCovidTest(idCovidTest);
        return mediator.handle(event);
    }

    @GetMapping(path="/tournaments/{idTournament}")
    public ResponseEntity getTournament(@PathVariable int idTournament){
        GetTournamentById event = new GetTournamentById();
        event.setIdTournament(idTournament);
        return mediator.handle(event);
    }

    @GetMapping(path="/matches/{idMatchT}")
    public ResponseEntity getMatchT(@PathVariable int idMatchT){
        GetMatchById event = new GetMatchById();
        event.setIdMatchT(idMatchT);
        return mediator.handle(event);
    }

    @PostMapping(path="/covidtests")
    public ResponseEntity<Void> createCovidTest(@RequestBody CovidTest covidTest){
        CovidTestCreated event = new CovidTestCreated();
        event.setCovidTest(covidTest);
        return mediator.handle(event);
    }

    @PostMapping(path="/fighters")
    public ResponseEntity<Void> createFighter(@RequestBody Fighter fighter){
        FighterCreated event = new FighterCreated();
        event.setFighter(fighter);
        return mediator.handle(event);
    }

    @PostMapping(path="/tournaments")
    public ResponseEntity<Void> createTournament(@RequestBody Tournament tournament){
        TournamentCreated event = new TournamentCreated();
        event.setTournament(tournament);
        return mediator.handle(event);
    }

    @PostMapping(path="/addTournament2")
    public ResponseEntity createTournament2(@RequestBody Tournament tournament){
        TournamentCreatedReturned event = new TournamentCreatedReturned();
        event.setTournament(tournament);
        return mediator.handle(event);
    }

    public void resetWeek(){
        this.weekFlag = 0;
    }

    @PutMapping(path="/week-reset")
    public ResponseEntity resetAppWeek(){
        resetWeek();
        return new ResponseEntity(HttpStatus.OK);
    }

    private int weekFlag = 0;

    private static int applyStrategy(Tournament tournament, int weekFlag){
        String tournamentType = tournament.getType();
        Context context;
        if(tournamentType.equals("Weekly")) {
            context = new Context(new WeeklyStrategy());
        }
        else{
            context = new Context(new MonthlyStrategy());
        }
        return context.executeStrategy(weekFlag);
    }

    @PostMapping(path="/matches")
    public ResponseEntity createMatchT(@RequestBody Tournament tournament){
        MatchCreated event = new MatchCreated();
        weekFlag = applyStrategy(tournament, weekFlag);
        event.setTournament(tournament);
        event.setWeekFlag(weekFlag);
        return mediator.handle(event);
    }

    @PutMapping(path="/fighters/{idFighter}")
    public ResponseEntity<Fighter> updateSaveFighter(@PathVariable int idFighter, @RequestBody Fighter fighter){
        FighterUpdated event = new FighterUpdated();
        event.setFighter(fighter);
        event.setIdFighter(idFighter);
        return mediator.handle(event);
    }

    @PutMapping(path="/covidtests/{idCovidTest}")
    public ResponseEntity<CovidTest> updateSaveCovidTest(@PathVariable int idCovidTest, @RequestBody CovidTest covidTest){
        CovidTestUpdated event = new CovidTestUpdated();
        event.setCovidTest(covidTest);
        event.setIdCovidTest(idCovidTest);
        return mediator.handle(event);
    }

    @PutMapping(path="/tournaments/{idTournament}")
    public ResponseEntity<Tournament> updateSaveTournament(@PathVariable int idTournament, @RequestBody Tournament tournament){
        TournamentUpdated event = new TournamentUpdated();
        event.setIdTournament(idTournament);
        event.setTournament(tournament);
        return mediator.handle(event);
    }

    @PutMapping(path="/matches/{idMatchT}")
    public ResponseEntity<MatchT> updateSaveMatchT(@PathVariable int idMatchT, @RequestBody MatchT match){
        MatchUpdated event = new MatchUpdated();
        event.setMatch(match);
        event.setIdMatchT(idMatchT);
        return mediator.handle(event);
    }

}
