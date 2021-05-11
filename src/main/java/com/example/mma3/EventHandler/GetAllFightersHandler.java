package com.example.mma3.EventHandler;

import com.example.mma3.Events.Queries.GetAllFighters;
import com.example.mma3.Service.FighterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetAllFightersHandler implements IEventHandler<GetAllFighters> {
    private final FighterService fighterService;

    @Override
    public ResponseEntity handle(GetAllFighters event) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(fighterService.findAll());
    }
}
