package com.quizapp.quizweb.repository;

import com.quizapp.quizweb.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {
}
