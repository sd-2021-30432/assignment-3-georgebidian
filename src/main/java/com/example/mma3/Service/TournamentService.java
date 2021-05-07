package com.example.mma3.Service;

import com.example.mma3.Model.Tournament;
import com.example.mma3.Repository.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TournamentService {
    private final TournamentRepository tournamentRepository;

    public List<Tournament> findAll(){
        return (List<Tournament>)tournamentRepository.findAll();
    }

    public void deleteById(int idTournament){
        tournamentRepository.deleteById(idTournament);
    }

    public Tournament findById(int idTournament){
        Optional<Tournament> result = tournamentRepository.findById(idTournament);
        if(result.isEmpty()){
            return null;
        }
        else return result.get();
    }

    public Tournament save(Tournament tournament){
        return tournamentRepository.save(tournament);
    }
}
