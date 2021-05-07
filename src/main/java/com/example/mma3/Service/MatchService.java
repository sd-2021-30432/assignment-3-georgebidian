package com.example.mma3.Service;

import com.example.mma3.Model.DTO.MatchDTO;
import com.example.mma3.Model.MatchT;
import com.example.mma3.Repository.FighterRepository;
import com.example.mma3.Repository.MatchRepository;
import com.example.mma3.Repository.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.mma3.Model.DTO.MatchDTO.fromEntity;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchRepository matchRepository;
    private final TournamentRepository tournamentRepository;
    private final FighterRepository fighterRepository;

    public List<MatchT> findAll(){
        return matchRepository.findAll();
    }
    public List<MatchDTO> findAllDTO(){
        return matchRepository.findAll().stream()
                .map(
                        match -> fromEntity(
                                match,
                                fighterRepository.findById(match.getIdFighter1()).get().getName(),
                                fighterRepository.findById(match.getIdFighter2()).get().getName(),
                                tournamentRepository.findById(match.getIdTournament()).get().getTitle(),
                                fighterRepository.findById(match.getWinner()).get().getName()
                                )
                )
                .collect(toList());
    }

    public void deleteById(int idMatch){
        matchRepository.deleteById(idMatch);
    }

    public MatchT findById(int idMatch){
        Optional<MatchT> result = matchRepository.findById(idMatch);
        if(result.isEmpty()){
            return null;
        }
        else return result.get();
    }

    public MatchT save(MatchT match){
        return matchRepository.save(match);
    }
}
