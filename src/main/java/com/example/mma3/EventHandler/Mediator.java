package com.example.mma3.EventHandler;

import com.example.mma3.Events.Commands.*;
import com.example.mma3.Events.Queries.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class Mediator {
    private HashMap<Class, Class> _handlerMap = new HashMap<>();

    @Autowired
    private ApplicationContext context;

    public Mediator(){
        _handlerMap.put(MatchCreated.class, MatchCreatedHandler.class);
        _handlerMap.put(GetAllCovidTests.class, GetAllCovidTestsHandler.class);
        _handlerMap.put(GetAllFighters.class, GetAllFightersHandler.class);
        _handlerMap.put(GetAllMatches.class, GetAllMatchesHandler.class);
        _handlerMap.put(GetAllTournaments.class, GetAllTournamentsHandler.class);
        _handlerMap.put(CovidTestDeleted.class, CovidTestDeletedHandler.class);
        _handlerMap.put(FighterDeleted.class, FighterDeletedHandler.class);
        _handlerMap.put(MatchDeleted.class, MatchDeletedHandler.class);
        _handlerMap.put(TournamentDeleted.class, TournamentDeletedHandler.class);
        _handlerMap.put(GetFighterById.class, GetFighterByIdHandler.class);
        _handlerMap.put(GetCovidTestById.class, GetCovidTestByIdHandler.class);
        _handlerMap.put(GetTournamentById.class, GetTournamentByIdHandler.class);
        _handlerMap.put(FighterCreated.class, FighterCreatedHandler.class);
        _handlerMap.put(CovidTestCreated.class, CovidTestCreatedHandler.class);
        _handlerMap.put(TournamentCreated.class, TournamentCreatedHandler.class);
        _handlerMap.put(TournamentCreatedReturned.class, TournamentCreatedReturnedHandler.class);
        _handlerMap.put(FighterUpdated.class, FighterUpdatedHandler.class);
        _handlerMap.put(CovidTestUpdated.class, CovidTestUpdatedHandler.class);
        _handlerMap.put(TournamentUpdated.class, TournamentUpdatedHandler.class);
        _handlerMap.put(MatchUpdated.class, MatchUpdatedHandler.class);
        _handlerMap.put(GetAllFightersDTO.class, GetAllFightersDTOHandler.class);
        //more handler TBA
    }

    public <T> ResponseEntity handle(T event){
        Class handlerType = _handlerMap.get(event.getClass());
        try {
            IEventHandler<T> handler = (IEventHandler<T>) context.getBean(handlerType);
            return handler.handle(event);
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
        }
        return null;
    }
}

