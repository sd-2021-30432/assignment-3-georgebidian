package com.example.mma3.EventHandler;

import com.example.mma3.Events.Commands.FighterCreated;
import com.example.mma3.Model.Fighter;
import com.example.mma3.Service.FighterService;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class FighterCreatedHandler implements IEventHandler<FighterCreated> {
    private final FighterService fighterService;

    @Override
    public ResponseEntity handle(FighterCreated event) {
        try{
            Fighter createdFighter = fighterService.save(event.getFighter());
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idFighter}").buildAndExpand(createdFighter.getIdFighter()).toUri();
            return ResponseEntity.created(uri).build();
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Foreign key constraint fails! Check covid tests ids.");
        }

    }
}
