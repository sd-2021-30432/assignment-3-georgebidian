package com.example.mma3.Repository;

import com.example.mma3.Model.Fighter;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FighterRepository extends CrudRepository<Fighter, Integer> {
}
