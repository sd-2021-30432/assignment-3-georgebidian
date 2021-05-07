package com.example.mma3.Service;

import com.example.mma3.Model.Fighter;
import com.example.mma3.Repository.FighterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FighterService {

    private final FighterRepository fighterRepository;

    public List<Fighter> findAll(){
        return (List<Fighter>)fighterRepository.findAll();
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
