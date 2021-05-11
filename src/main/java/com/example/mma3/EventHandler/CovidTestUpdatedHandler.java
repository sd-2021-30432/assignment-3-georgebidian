package com.example.mma3.EventHandler;

import com.example.mma3.Events.Commands.CovidTestUpdated;
import com.example.mma3.Model.CovidTest;
import com.example.mma3.Service.CovidTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CovidTestUpdatedHandler implements IEventHandler<CovidTestUpdated> {
    private final CovidTestService covidTestService;
    @Override
    public ResponseEntity handle(CovidTestUpdated event) {
        CovidTest covidTestSaved;
        CovidTest covidTest = event.getCovidTest();
        if(event.getIdCovidTest() == -1){
            covidTestSaved = covidTestService.save(covidTest);
        }
        else{
            CovidTest ct = covidTestService.findById(event.getIdCovidTest());
            ct.setIdCovidTest(covidTest.getIdCovidTest());
            ct.setTestDate(covidTest.getTestDate());
            ct.setResult(covidTest.isResult());
            covidTestSaved = covidTestService.save(ct);
        }
        return new ResponseEntity<CovidTest>(covidTestSaved, HttpStatus.OK);
    }
}
