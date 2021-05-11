package com.example.mma3.EventHandler;

import com.example.mma3.Events.Queries.GetAllCovidTests;
import com.example.mma3.Service.CovidTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetAllCovidTestsHandler implements IEventHandler<GetAllCovidTests> {
    private final CovidTestService covidTestService;

    @Override
    public ResponseEntity handle(GetAllCovidTests event) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(covidTestService.findAll());
    }
}
