package com.quizapp.quizweb.service;

import com.quizapp.quizweb.model.Question;
import com.quizapp.quizweb.model.Quiz;
import com.quizapp.quizweb.model.User;
import com.quizapp.quizweb.observerfactory.QuizFactory;
import com.quizapp.quizweb.observerfactory.QuizNotifier;
import com.quizapp.quizweb.observerfactory.UserObserver;
import com.quizapp.quizweb.repository.QuizRepository;
import com.quizapp.quizweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizNotifier quizNotifier;

    @Autowired
    private UserRepository userRepository;

    public String createQuiz(String topicName, List <Question> questions) {
        Quiz quiz = QuizFactory.createQuiz(topicName,questions);
        quizRepository.save(quiz);

        List<User> allUsers = userRepository.findAll();
        for (User user : allUsers) {
            UserObserver observer = new UserObserver(user);
            observer.update(topicName); // prints message
        }


        return "Quiz created and users have been notified!";
    }

    public List<String> getAllQuizTopics() {
        List<Quiz> quizzes = quizRepository.findAll();
        return quizzes.stream()
                .map(Quiz::getTopic)
                .toList(); // Java 16+ (or use .collect(Collectors.toList()))
    }


    public Optional<Quiz> getQuizTopicById(int id) {
        return quizRepository.findById(id);
    }
}
