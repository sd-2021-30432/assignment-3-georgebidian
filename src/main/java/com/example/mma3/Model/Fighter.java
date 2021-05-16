package com.example.mma3.Model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Fighter implements IFighter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idFighter;
    private String firstname;
    private String lastname;
    private float weight;
    private int initialTestId;
    private int secondTestId;
    private boolean inQuarantine;
    private int countNegatives;

    public String getName(){
        return firstname + " " + lastname;
    }

    public void incrementNegatives(){
        this.countNegatives++;
    }

    @Override
    public String getColor() {
        return "lightgray";
    }
}
