package com.quizapp.quizweb.observerfactory;

import com.quizapp.quizweb.model.User;
import com.quizapp.quizweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuizNotifier implements Subject{


    private final List<Observer> observers = new ArrayList<>();
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String topicName) {
        List<User> allUsers = userRepository.findAll();
        for (User user : allUsers) {
            UserObserver observer = new UserObserver(user, mailSender);
            System.out.println("User " + user.getId() + " has been notified!");
            observer.update(topicName);
        }
    }
}
