package com.example.mma3.Events.Queries;

import com.example.mma3.Model.DTO.MatchDTO;
import lombok.Data;
import java.util.List;

@Data
public class GetAllMatches {
    List<MatchDTO> matches;
}
