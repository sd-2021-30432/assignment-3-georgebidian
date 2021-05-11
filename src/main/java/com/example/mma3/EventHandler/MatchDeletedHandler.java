package com.example.mma3.EventHandler;

import com.example.mma3.Events.Commands.MatchDeleted;
import com.example.mma3.Service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchDeletedHandler implements IEventHandler<MatchDeleted>{
    private final MatchService matchService;

    @Override
    public ResponseEntity handle(MatchDeleted event) {
        try{
            matchService.deleteById(event.getIdMatchT());
        }
        catch(Exception e){
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
