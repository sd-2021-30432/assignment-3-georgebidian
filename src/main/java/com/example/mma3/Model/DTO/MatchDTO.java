package com.example.mma3.Model.DTO;

import com.example.mma3.Model.MatchT;
import com.example.mma3.Repository.FighterRepository;
import com.example.mma3.Repository.TournamentRepository;
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
    public static MatchDTO fromEntity(MatchT match, FighterRepository fighterRepository, TournamentRepository tournamentRepository){
        return MatchDTO.builder()
                .idMatch(match.getIdMatchT())
                .nameFighter1(fighterRepository.findById(match.getIdFighter1()).get().getName())
                .nameFighter2(fighterRepository.findById(match.getIdFighter2()).get().getName())
                .tournamentTitle(tournamentRepository.findById(match.getIdTournament()).get().getTitle())
                .winner(fighterRepository.findById(match.getWinner()).get().getName())
                .rounds(match.getRounds())
                .dateTimeStart(match.getDateTimeStart())
                .build();
    }
}
