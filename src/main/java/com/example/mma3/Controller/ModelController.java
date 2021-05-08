package com.example.mma3.Controller;

import com.example.mma3.EventHandler.MatchCreatedHandler;
import com.example.mma3.EventHandler.Mediator;
import com.example.mma3.Events.Commands.MatchCreated;
import com.example.mma3.Model.*;
import com.example.mma3.Model.DTO.MatchDTO;
import com.example.mma3.Service.CovidTestService;
import com.example.mma3.Service.FighterService;
import com.example.mma3.Service.MatchService;
import com.example.mma3.Service.TournamentService;
import com.example.mma3.Strategy.Context;
import com.example.mma3.Strategy.MonthlyStrategy;
import com.example.mma3.Strategy.WeeklyStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/mma/data")
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
public class ModelController {
    private final CovidTestService covidTestService;
    private final MatchService matchService;
    private final FighterService fighterService;
    private final TournamentService tournamentService;
    private Mediator mediator;

    @GetMapping(path="/covidtests")
    public List<CovidTest> getAllTests(){
        return covidTestService.findAll();
    }

    @GetMapping(path="/fighters")
    public List<Fighter> getAllFighters(){
        return fighterService.findAll();
    }

    @GetMapping(path="/matches")
    public List<MatchDTO> getAllMatches(){
        return matchService.findAllDTO();
    }

    @GetMapping(path="/tournaments")
    public List<Tournament> getAllTournaments(){
        return tournamentService.findAll();
    }

    @DeleteMapping(path="/deleteFighter/{idFighter}")
    public ResponseEntity deleteFighter(@PathVariable int idFighter){
        try{
            fighterService.deleteById(idFighter);
        }
        catch(DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Fighter with id:" + idFighter + " is currently used in a match");
        }
        catch(Exception e){
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path="/deleteCovidTest/{idCovidTest}")
    public ResponseEntity deleteCovidTest(@PathVariable int idCovidTest){
        try{
            covidTestService.deleteById(idCovidTest);
        }
        catch(DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Covid Test with id:" + idCovidTest + " is currently used by a fighter");
        }
        catch (Exception e){
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path="/deleteTournament/{idTournament}")
    public ResponseEntity deleteTournament(@PathVariable int idTournament){
        try{
            tournamentService.deleteById(idTournament);
        }
        catch(DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Tournament with id:" + idTournament + " is currently used for a match");
        }
        catch(Exception e){
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path="/deleteMatch/{idMatchT}")
    public ResponseEntity<Void> deleteMatch(@PathVariable int idMatchT){
        try{
            matchService.deleteById(idMatchT);
        }
        catch(Exception e){
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }



    @GetMapping(path="/fighters/{idFighter}")
    public Fighter getFighter(@PathVariable int idFighter){
        if(idFighter != -1){
            Fighter fighter = fighterService.findById(idFighter);
            if(fighter == null)
                throw new IllegalArgumentException("Failed: No Fighter with ID=" + idFighter);
            else return fighter;
        }
        return new Fighter();
    }

    @GetMapping(path="/covidtests/{idCovidTest}")
    public CovidTest getCovidTest(@PathVariable int idCovidTest){
        if(idCovidTest != -1){
            CovidTest covidTest = covidTestService.findById(idCovidTest);
            if(covidTest == null)
                throw new IllegalArgumentException("Failed: No Covid Test with ID=" + idCovidTest);
            else return covidTest;
        }
        return new CovidTest();
    }

    @GetMapping(path="/tournaments/{idTournament}")
    public Tournament getTournament(@PathVariable int idTournament){
        if(idTournament != -1){
            Tournament tournament = tournamentService.findById(idTournament);
            if(tournament == null)
                throw new IllegalArgumentException("Failed: No Tournament with ID=" + idTournament);
            else return tournament;
        }
        return new Tournament();
    }

    @GetMapping(path="/matches/{idMatchT}")
    public MatchT getMatchT(@PathVariable int idMatchT){
        if(idMatchT != -1){
            MatchT match = matchService.findById(idMatchT);
            if(match == null)
                throw new IllegalArgumentException("Failed: No Match with ID=" + idMatchT);
            else return match;
        }
        return new MatchT();
    }

    @PostMapping(path="/covidtests")
    public ResponseEntity<Void> createCovidTest(@RequestBody CovidTest covidTest){
        CovidTest createdCovidTest = covidTestService.save(covidTest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idCovidTest}").buildAndExpand(createdCovidTest.getIdCovidTest()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PostMapping(path="/fighters")
    public ResponseEntity<Void> createFighter(@RequestBody Fighter fighter){
        Fighter createdFighter = fighterService.save(fighter);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idFighter}").buildAndExpand(createdFighter.getIdFighter()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PostMapping(path="/tournaments")
    public ResponseEntity<Void> createTournament(@RequestBody Tournament tournament){
        Tournament createdTournament = tournamentService.save(tournament);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idTournament}").buildAndExpand(createdTournament.getIdTournament()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PostMapping(path="/addTournament2")
    public Tournament createTournament2(@RequestBody Tournament tournament){
        Tournament createdTournament = tournamentService.save(tournament);
        return createdTournament;
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
        mediator = new Mediator(covidTestService, matchService, fighterService, tournamentService);
        MatchCreated event = new MatchCreated();
        weekFlag = applyStrategy(tournament, weekFlag);
        event.setTournament(tournament);
        event.setWeekFlag(weekFlag);
        return mediator.handle(event);
    }

   /* @PostMapping(path="/addCovitTest")
    public @ResponseBody CovidTest addNewCovidTest(@RequestParam String testDate, @RequestParam(required = false) String result){
        CovidTest ct = new CovidTest();
        ct.setTestDate(testDate);
        boolean newResult;
        if(result == null)
            newResult = generateCovidResult(10);
        else
            newResult = result.equals("true");
        ct.setResult(newResult);
        CovidTest ctSaved = covidTestService.save(ct);
        return ctSaved;
    }*/

    private boolean isFighterIDUsed(int id){
        ArrayList<Fighter> fighters = (ArrayList<Fighter>) fighterService.findAll();
        for(Fighter f : fighters){
            if(f.getInitialTestId() == id || f.getSecondTestId() == id)
                return true;
        }
        return false;
    }

    @PutMapping(path="/fighters/{idFighter}")
    public ResponseEntity<Fighter> updateSaveFighter(@PathVariable int idFighter, @RequestBody Fighter fighter){
        Fighter fighterSaved;
        if(idFighter == -1){
            fighterSaved = fighterService.save(fighter);
        }
        else{
            Fighter f = fighterService.findById(idFighter);
            f.setFirstname(fighter.getFirstname());
            f.setLastname(fighter.getLastname());
            f.setWeight(fighter.getWeight());
            f.setWins(fighter.getWins());
            f.setInQuarantine(fighter.isInQuarantine());
            f.setInitialTestId(fighter.getInitialTestId());
            f.setSecondTestId(fighter.getSecondTestId());
            fighterSaved = fighterService.save(f);
        }
        return new ResponseEntity<Fighter>(fighterSaved, HttpStatus.OK);
    }

    @PutMapping(path="/covidtests/{idCovidTest}")
    public ResponseEntity<CovidTest> updateSaveCovidTest(@PathVariable int idCovidTest, @RequestBody CovidTest covidTest){
        CovidTest covidTestSaved;
        if(idCovidTest == -1){
            covidTestSaved = covidTestService.save(covidTest);
        }
        else{
            CovidTest ct = covidTestService.findById(idCovidTest);
            ct.setIdCovidTest(covidTest.getIdCovidTest());
            ct.setTestDate(covidTest.getTestDate());
            ct.setResult(covidTest.isResult());
            covidTestSaved = covidTestService.save(ct);
        }
        return new ResponseEntity<CovidTest>(covidTestSaved, HttpStatus.OK);
    }

    @PutMapping(path="/tournaments/{idTournament}")
    public ResponseEntity<Tournament> updateSaveTournament(@PathVariable int idTournament, @RequestBody Tournament tournament){
        Tournament tournamentSaved;
        if(idTournament == -1){
            tournamentSaved = tournamentService.save(tournament);
        }
        else{
            Tournament t = tournamentService.findById(idTournament);
            t.setIdTournament(tournament.getIdTournament());
            t.setTitle(tournament.getTitle());
            t.setLocation(tournament.getLocation());
            t.setDateTimeStart(tournament.getDateTimeStart());
            t.setType(tournament.getType());
            tournamentSaved = tournamentService.save(t);
        }
        return new ResponseEntity<Tournament>(tournamentSaved, HttpStatus.OK);
    }

    @PutMapping(path="/matches/{idMatchT}")
    public ResponseEntity<MatchT> updateSaveMatchT(@PathVariable int idMatchT, @RequestBody MatchT match){
        MatchT matchSaved;
        if(idMatchT == -1){
            matchSaved = matchService.save(match);
        }
        else{
            MatchT m = matchService.findById(idMatchT);
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

    @PostMapping(path="/addFighter")
    public @ResponseBody
    Fighter addNewFighter(@RequestParam String firstname, @RequestParam String lastname, @RequestParam String weight, @RequestParam String initialTestId, @RequestParam(required = false) String secondTestId, @RequestParam(required = false) String inQuarantine, @RequestParam(required = false) String wins){
        Fighter f = new Fighter();
        f.setFirstname(firstname);
        f.setLastname(lastname);
        f.setWeight(Float.parseFloat(weight));

        if(isFighterIDUsed(Integer.parseInt(initialTestId)))
            throw new IllegalArgumentException( "Failed: Covid Test already used: ID=" + initialTestId);
        if(covidTestService.findById(Integer.parseInt(initialTestId)) != null)
            f.setInitialTestId(Integer.parseInt(initialTestId));
        else
            throw new IllegalArgumentException( "Failed: Can not find initial Covid Test with the ID=" + initialTestId);
        if(secondTestId != null) {
            if(isFighterIDUsed(Integer.parseInt(secondTestId)))
                throw new IllegalArgumentException("Failed: Covid Test already used: ID=" + secondTestId);
            if (covidTestService.findById(Integer.parseInt(secondTestId)) != null)
                f.setSecondTestId(Integer.parseInt(secondTestId));
            else
                throw new IllegalArgumentException("Failed: Can not find second Covid Test with the ID=" + secondTestId);
        }
        else
            f.setSecondTestId(0);
        if(inQuarantine == null)
            f.setInQuarantine(false);
        else {
            boolean res = inQuarantine.equals("true") || inQuarantine.equals("1");
            f.setInQuarantine(res);
        }
        if(wins == null)
            f.setWins(0);
        else
            f.setWins(Integer.parseInt(wins));

        Fighter fSaved = fighterService.save(f);
        return fSaved;
    }

    @PostMapping(path="/addTournament")
    public @ResponseBody String addNewTournament(@RequestParam String title, @RequestParam String location, @RequestParam String dateTimeStart, @RequestParam String type){
        Tournament t = new Tournament();
        t.setTitle(title);
        t.setLocation(location);
        t.setDateTimeStart(dateTimeStart);
        t.setType(type);
        Tournament tSaved = tournamentService.save(t);
        return tSaved.toString();
    }

    @PostMapping(path="/addMatch")
    public @ResponseBody String addNewMatch(@RequestParam String idFighter1, @RequestParam String idFighter2, @RequestParam String idTournament, @RequestParam(required = false) String winner, @RequestParam(required = false) String rounds, @RequestParam String dateTimeStart){
        MatchT m = new MatchT();

        if(fighterService.findById(Integer.parseInt(idFighter1)) != null)
            m.setIdFighter1(Integer.parseInt(idFighter1));
        else
            return "Failed: Can not find fighter1 with the ID=" + idFighter1;
        if(fighterService.findById(Integer.parseInt(idFighter2)) != null)
            m.setIdFighter2(Integer.parseInt(idFighter2));
        else
            return "Failed: Can not find fighter2 with the ID=" + idFighter2;
        if(tournamentService.findById(Integer.parseInt(idTournament)) != null)
            m.setIdTournament(Integer.parseInt(idTournament));
        else
            return "Failed: Can not find tournament with the ID=" + idTournament;
        if(winner != null)
            if(fighterService.findById(Integer.parseInt(winner)) != null)
                m.setWinner(Integer.parseInt(winner));
            else
                return "Failed: Can not find winner(fighter) with the ID=" + winner;
        if(rounds != null)
            m.setRounds(Integer.parseInt(rounds));
        else
            m.setRounds(0);

        m.setDateTimeStart(dateTimeStart);

        MatchT mSaved = matchService.save(m);

        return mSaved.toString();
    }

}
