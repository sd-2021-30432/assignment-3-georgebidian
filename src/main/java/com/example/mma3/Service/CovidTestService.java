package com.example.mma3.Service;

import com.example.mma3.Model.CovidTest;
import com.example.mma3.Repository.CovidTestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CovidTestService {

    private final CovidTestRepository covidTestRepository;

    public List<CovidTest> findAll(){
        return (List<CovidTest>)covidTestRepository.findAll();
    }

    public void deleteById(int idCovidTest){
        covidTestRepository.deleteById(idCovidTest);
    }

    public CovidTest findById(int idCovidTest){
        Optional<CovidTest> result = covidTestRepository.findById(idCovidTest);
        if(result.isEmpty()){
            return null;
        }
        else return result.get();
    }

    public CovidTest save(CovidTest covidTest){
        return covidTestRepository.save(covidTest);
    }
}
