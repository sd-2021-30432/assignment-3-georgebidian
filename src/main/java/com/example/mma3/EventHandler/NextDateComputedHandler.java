package com.example.mma3.EventHandler;

import com.example.mma3.Events.Commands.NextDateComputed;
import com.example.mma3.Strategy.Context;
import com.example.mma3.Strategy.MonthlyStrategy;
import com.example.mma3.Strategy.WeeklyStrategy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Component
public class NextDateComputedHandler implements IEventHandler<NextDateComputed>{

    @Override
    public ResponseEntity handle(NextDateComputed event) {
        String tournamentType = event.getTournamentType();
        String date = event.getCurrentDate();
        Context context;
        if(tournamentType.equals("Weekly")) {
            context = new Context(new WeeklyStrategy());
        }
        else{
            context = new Context(new MonthlyStrategy());
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(context.executeStrategy(date));
    }
}
