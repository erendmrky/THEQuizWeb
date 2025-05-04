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
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuizNotifier quizNotifier;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserRepository userRepository;

    public String createQuiz(String topicName, List <Question> questions) {
        Quiz quiz = QuizFactory.createQuiz(topicName,questions);
        quizRepository.save(quiz);
        List<User> allUsers = userRepository.findAll();
        for (User user : allUsers) {
            UserObserver observer = new UserObserver(user,mailSender);
            observer.update(topicName);
        }
        return "Quiz created and users have been notified!";
    }

    public String createQuestions(int id, List<Question> questions) {
        Quiz quiz = quizRepository.findById(id).get();

        for(Question question : questions) {
            question.setQuiz(quiz);
        }
        quiz.getQuestions().addAll(questions);
        quizRepository.save(quiz);

        List<User> allUsers = userRepository.findAll();
        for (User user : allUsers) {
            UserObserver observer = new UserObserver(user,mailSender);
            quizNotifier.notifyObservers(quiz.getTopic());
        }
        return "Questions created for existing topic and users have been notified!";
    }

    public List<Question> getQuizTopicById(int id) {
        Quiz quiz = quizRepository.findById(id).get();

        List<Question> shuffledQuestions = quiz.getQuestions();
        Collections.shuffle(shuffledQuestions);
        return shuffledQuestions;
    }

    public int calculateScoreForTopic(int quizId, int userId, Map<Integer, String> answers) {
        User user = userRepository.findById(userId).get();

        int correctAnswers = 0;
        for (Map.Entry<Integer, String> entry : answers.entrySet()) {
            Question q = quizRepository.findById(quizId)
                    .get()
                    .getQuestions()
                    .stream()
                    .filter(x -> x.getId() == entry.getKey())
                    .findFirst()
                    .orElse(null);
            if (q == null) break;

            if (q.getCorrectOption().equalsIgnoreCase(entry.getValue().trim())) {
                correctAnswers++;
            } else {
                break;
            }
        }
        switch (quizId){
            case 1:
                if(user.getNumericalBestScore() < correctAnswers) {
                    user.setNumericalBestScore(correctAnswers);
                }
                break;
            case 2:
                if(user.getVerbalBestScore() < correctAnswers) {
                    user.setVerbalBestScore(correctAnswers);
                }
                break;
            case 3:
                if(user.getNonverbalBestScore() < correctAnswers) {
                    user.setNonverbalBestScore(correctAnswers);
                }
                break;
            case 4:
                if(user.getMechanicalBestScore() < correctAnswers) {
                    user.setMechanicalBestScore(correctAnswers);
                }
                break;
        }
        userRepository.save(user);
        return correctAnswers;
    }
}