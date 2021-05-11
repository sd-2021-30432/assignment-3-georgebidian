package com.example.mma3.EventHandler;

import com.example.mma3.Events.Queries.GetAllMatches;
import com.example.mma3.Service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetAllMatchesHandler implements IEventHandler<GetAllMatches> {
    private final MatchService matchService;

    @Override
    public ResponseEntity handle(GetAllMatches event) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(matchService.findAllDTO());
    }
}
