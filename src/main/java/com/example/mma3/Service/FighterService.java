package com.example.mma3.Service;

import com.example.mma3.Model.DTO.FighterDTO;
import com.example.mma3.Model.Fighter;
import com.example.mma3.Repository.FighterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FighterService {

    private final FighterRepository fighterRepository;

    public List<Fighter> findAll(){
        return (List<Fighter>)fighterRepository.findAll();
    }

    public List<FighterDTO> findAllDTO(){
        return fighterRepository.findAll().stream()
               .map(FighterDTO::fromFighter)
                .collect(Collectors.toList());

    }

    public void deleteById(int idFighter){
        fighterRepository.deleteById(idFighter);
    }

    public Fighter findById(int idFighter){
        Optional<Fighter> result = fighterRepository.findById(idFighter);
        if(result.isEmpty()){
            return null;
        }
        else return result.get();
    }

    public Fighter save(Fighter fighter){
        return fighterRepository.save(fighter);
    }
}
