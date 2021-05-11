package com.example.mma3.EventHandler;

import com.example.mma3.Events.Commands.FighterDeleted;
import com.example.mma3.Service.FighterService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FighterDeletedHandler implements IEventHandler<FighterDeleted>{
    private final FighterService fighterService;

    @Override
    public ResponseEntity handle(FighterDeleted event) {
        try{
            fighterService.deleteById(event.getIdFighter());
        }
        catch(DataIntegrityViolationException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Fighter with id:" + event.getIdFighter() + " is currently used in a match");
        }
        catch(Exception e){
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
