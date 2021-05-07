package com.example.mma3.Repository;

import com.example.mma3.Model.CovidTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CovidTestRepository extends CrudRepository<CovidTest, Integer>{
}
