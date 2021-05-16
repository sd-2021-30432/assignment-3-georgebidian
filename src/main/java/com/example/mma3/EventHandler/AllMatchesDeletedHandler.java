package com.example.mma3.EventHandler;

import com.example.mma3.Events.Commands.AllMatchesDeleted;
import com.example.mma3.Model.MatchT;
import com.example.mma3.Service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AllMatchesDeletedHandler implements IEventHandler<AllMatchesDeleted> {

    private final MatchService matchService;

    @Override
    public ResponseEntity handle(AllMatchesDeleted event) {
        List<MatchT> matches = matchService.findAll();
        for(MatchT match : matches){
            try{
                matchService.deleteById(match.getIdMatchT());
            }
            catch (Exception e){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Can no delete match " + match.getIdMatchT());
            }
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("All matches have been deleted!");
    }
}
