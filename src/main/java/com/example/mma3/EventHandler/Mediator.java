package com.example.mma3.EventHandler;

import com.example.mma3.Events.Commands.MatchCreated;
import com.example.mma3.Service.CovidTestService;
import com.example.mma3.Service.FighterService;
import com.example.mma3.Service.MatchService;
import com.example.mma3.Service.TournamentService;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Constructor;
import java.util.HashMap;

public class Mediator {
    private HashMap<Class, Class> _handlerMap = new HashMap<>();

    private final CovidTestService covidTestService;
    private final MatchService matchService;
    private final FighterService fighterService;
    private final TournamentService tournamentService;

    public Mediator(CovidTestService covidTestService, MatchService matchService, FighterService fighterService, TournamentService tournamentService){
        this.covidTestService = covidTestService;
        this.matchService = matchService;
        this.fighterService = fighterService;
        this.tournamentService = tournamentService;

        _handlerMap.put(MatchCreated.class, MatchCreatedHandler.class);
        //more handler TBA
    }

    public <T> ResponseEntity handle(T event){
        Class handlerType = _handlerMap.get(event.getClass());
        try {
            Constructor ctr = handlerType.getConstructor(CovidTestService.class, MatchService.class, FighterService.class, TournamentService.class);
            IEventHandler<T> handler = (IEventHandler<T>) ctr.newInstance(new Object[] {covidTestService, matchService, fighterService, tournamentService});
            return handler.handle(event);
        }
        catch(Exception e)
        {
            System.err.println(e.getStackTrace());
        }
        return null;
    }
}

