package com.quizapp.quizweb.controller;

import com.quizapp.quizweb.model.Question;
import com.quizapp.quizweb.model.Quiz;
import com.quizapp.quizweb.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("/create")
    public String createQuiz(@RequestParam String topicName, @RequestBody List<Question> questions) {
        return quizService.createQuiz(topicName,questions);
    }

    @GetMapping("/topics")
    public List<String> getAllQuizTopics() {
        return quizService.getAllQuizTopics();
    }

    @GetMapping("/topic/{id}")
    public Optional<Quiz> getQuizTopicById(@PathVariable int id) {
        return quizService.getQuizTopicById(id);
    }

}
