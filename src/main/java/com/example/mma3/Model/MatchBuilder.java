package com.example.mma3.Model;

import java.util.Random;

public class MatchBuilder {
    private MatchT matchT = new MatchT();
    private int idFighter1;
    private int idFighter2;
    private int idTournament;
    private int winner;
    private int rounds;
    private String dateTimeStart;

    public MatchBuilder addIdFighter1(int idFighter1){
        this.idFighter1 = idFighter1;
        return this;
    }

    public MatchBuilder addIdFighter2(int idFighter2){
        this.idFighter2 = idFighter2;
        return this;
    }

    public MatchBuilder addIdTournament(int idTournament){
        this.idTournament = idTournament;
        return this;
    }

    public MatchBuilder addWinner(){
        this.winner = new Random().nextBoolean() ? this.idFighter1 : this.idFighter2;;
        return this;
    }

    public MatchBuilder addRounds(){
        this.rounds = (int) ((Math.random() * 4) + 1);
        return this;
    }

    public MatchBuilder addDateTimeStart(String dateTimeStart){
        this.dateTimeStart = dateTimeStart;
        return this;
    }

    public MatchT build(){
        MatchT newMatchT = new MatchT();

        buildIdFighter1(newMatchT);
        buildIdFighter2(newMatchT);
        buildIdTournament(newMatchT);
        buildIdFighter1(newMatchT);
        buildIdFighter2(newMatchT);
        buildWinner(newMatchT);
        buildRounds(newMatchT);
        buildDateTimeStart(newMatchT);

        return newMatchT;
    }

    private void buildIdFighter1(MatchT newMatchT){
        newMatchT.idFighter1 = this.idFighter1;
    }

    private void buildIdFighter2(MatchT newMatchT){
        newMatchT.idFighter2 = this.idFighter2;
    }

    private void buildIdTournament(MatchT newMatchT){
        newMatchT.idTournament = this.idTournament;
    }

    private void buildWinner(MatchT newMatchT){
        newMatchT.winner = this.winner;
    }

    private void buildRounds(MatchT newMatchT){
        newMatchT.rounds = this.rounds;
    }

    private void buildDateTimeStart(MatchT newMatchT){
        newMatchT.dateTimeStart = this.dateTimeStart;
    }
}
