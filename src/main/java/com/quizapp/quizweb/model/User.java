package com.quizapp.quizweb.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name= "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique=true)
    private String email;
    private String password;
    @Column(unique=true)
    private String username;
    private float numericalBestScore;
    private float verbalBestScore;
    private float nonverbalBestScore;
    private float mechanicalBestScore;
    private float bestTotalScore;
}
