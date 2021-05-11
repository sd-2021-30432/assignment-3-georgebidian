package com.example.mma3.EventHandler;

import com.example.mma3.Events.Queries.GetMatchById;
import com.example.mma3.Model.MatchT;
import com.example.mma3.Service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetMatchByIdHandler implements IEventHandler<GetMatchById> {
    private final MatchService matchService;

    @Override
    public ResponseEntity handle(GetMatchById event) {
        if(event.getIdMatchT() != -1){
            MatchT match = matchService.findById(event.getIdMatchT());
            if(match == null)
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Failed: No Match with ID=" + event.getIdMatchT());
            else return ResponseEntity.status(HttpStatus.ACCEPTED).body(match);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MatchT());
    }
}
