package com.example.mma3.Model.DTO;

import com.example.mma3.Model.MatchT;
import com.example.mma3.Service.FighterService;
import com.example.mma3.Service.TournamentService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MatchDTO{
    private int idMatch;
    private String nameFighter1;
    private String nameFighter2;
    private String tournamentTitle;
    private String winner;
    private int rounds;
    private String dateTimeStart;

    private static FighterService fighterService;
    private static TournamentService tournamentService;

    public static MatchDTO fromEntity(MatchT match, String fighter1, String fighter2, String tournamentTitle, String winner){
        return MatchDTO.builder()
                .idMatch(match.getIdMatchT())
                .nameFighter1(fighter1)
                .nameFighter2(fighter2)
                .tournamentTitle(tournamentTitle)
                .winner(winner)
                .rounds(match.getRounds())
                .dateTimeStart(match.getDateTimeStart())
                .build();
    }
}
