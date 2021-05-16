package com.example.mma3.Events.Queries;

import com.example.mma3.Model.DTO.FighterDTO;
import lombok.Data;

import java.util.List;

@Data
public class GetAllFightersDTO {
    private List<FighterDTO> fighters;
}
