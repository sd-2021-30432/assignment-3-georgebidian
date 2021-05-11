package com.example.mma3.EventHandler;

import com.example.mma3.Events.Commands.CovidTestDeleted;
import com.example.mma3.Service.CovidTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CovidTestDeletedHandler implements IEventHandler<CovidTestDeleted>{
    private final CovidTestService covidTestService;

    @Override
    public ResponseEntity handle(CovidTestDeleted event) {
        try{
            covidTestService.deleteById(event.getIdCovidTest());
        }
        catch(DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Covid Test with id:" + event.getIdCovidTest() + " is currently used by a fighter");
        }
        catch(Exception e){
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
