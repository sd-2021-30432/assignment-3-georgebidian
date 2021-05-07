package com.example.mma3.Model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class CovidTest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idCovidTest;
    private String testDate;
    private boolean result;
}
