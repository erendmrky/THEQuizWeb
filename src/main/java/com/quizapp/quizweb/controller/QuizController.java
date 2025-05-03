package com.quizapp.quizweb.controller;

import com.quizapp.quizweb.model.Question;
import com.quizapp.quizweb.model.Quiz;
import com.quizapp.quizweb.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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

    @PutMapping("topic/{id}/newquestions")
    public String createQuestions(@PathVariable int id, @RequestBody List<Question> questions) {
        return quizService.createQuestions(id,questions);
    }

    @GetMapping("/topics")
    public List<String> getAllQuizTopics() {
        return quizService.getAllQuizTopics();
    }

    @GetMapping("/topic/{id}")
    public List<Question> getQuizTopicById(@PathVariable int id) {
        return quizService.getQuizTopicById(id);
    }

    @PostMapping("/topic/{id}/submit")
    public float submitQuestion(@PathVariable int id, @RequestParam int userId, @RequestBody Map<Integer, String> answers) {
        return quizService.calculateScoreForTopic(id,userId,answers);
    }
}
