package com.example.mma3.Repository;

import com.example.mma3.Model.MatchT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<MatchT, Integer> {
}
