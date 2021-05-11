package com.example.mma3.Events.Queries;

import com.example.mma3.Model.Fighter;
import lombok.Data;

import java.util.List;

@Data
public class GetAllFighters {
    List<Fighter> fighters;
}
