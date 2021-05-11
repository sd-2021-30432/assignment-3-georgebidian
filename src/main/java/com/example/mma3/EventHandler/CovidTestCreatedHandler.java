package com.example.mma3.EventHandler;

import com.example.mma3.Events.Commands.CovidTestCreated;
import com.example.mma3.Model.CovidTest;
import com.example.mma3.Service.CovidTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class CovidTestCreatedHandler implements IEventHandler<CovidTestCreated>{
    private final CovidTestService covidTestService;

    @Override
    public ResponseEntity handle(CovidTestCreated event) {
        CovidTest createdCovidTest = covidTestService.save(event.getCovidTest());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idCovidTest}").buildAndExpand(createdCovidTest.getIdCovidTest()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
