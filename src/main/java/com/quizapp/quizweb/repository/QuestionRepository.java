package com.quizapp.quizweb.repository;

import com.quizapp.quizweb.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
}
