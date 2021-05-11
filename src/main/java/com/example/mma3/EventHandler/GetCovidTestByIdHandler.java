package com.example.mma3.EventHandler;

import com.example.mma3.Events.Queries.GetCovidTestById;
import com.example.mma3.Model.CovidTest;
import com.example.mma3.Service.CovidTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetCovidTestByIdHandler implements IEventHandler<GetCovidTestById> {
    private final CovidTestService covidTestService;

    @Override
    public ResponseEntity handle(GetCovidTestById event) {
        if(event.getIdCovidTest() != -1){
            CovidTest covidTest = covidTestService.findById(event.getIdCovidTest());
            if(covidTest == null)
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Failed: No Covid Test with ID=" + event.getIdCovidTest());
            else  return ResponseEntity.status(HttpStatus.ACCEPTED).body(covidTest);
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new CovidTest());
    }
}
