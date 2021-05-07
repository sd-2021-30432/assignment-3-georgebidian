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
public class MatchT {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idMatchT;
    public int idFighter1;
    public int idFighter2;
    public int idTournament;
    public int winner;
    public int rounds;
    public String dateTimeStart;
}
