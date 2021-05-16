package com.example.mma3;

import com.example.mma3.Model.Fighter;
import com.example.mma3.Model.IFighter;
import com.example.mma3.Model.PositiveFighter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Mma3Application {

    public static void main(String[] args) {
        SpringApplication.run(Mma3Application.class, args);
        Fighter gigel = new Fighter();
        gigel.setFirstname("Gigel");
        gigel.setLastname("Danescu");
        gigel.setInitialTestId(0);
        gigel.setInQuarantine(false);
        gigel.setSecondTestId(0);
        gigel.setColor("pla");
        System.out.println(gigel);
        IFighter fighterRed = new PositiveFighter(new Fighter());
        fighterRed.changeColor("red");
        System.out.println(gigel);
    }

}
