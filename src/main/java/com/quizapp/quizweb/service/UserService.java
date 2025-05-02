package com.quizapp.quizweb.service;

import com.quizapp.quizweb.model.User;
import com.quizapp.quizweb.observerfactory.QuizNotifier;
import com.quizapp.quizweb.observerfactory.UserObserver;
import com.quizapp.quizweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @Autowired
    private QuizNotifier quizNotifier;
    @Autowired
    private JavaMailSender mailSender;

    public String registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return "Email Already Exists";
        }
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);

        UserObserver userObserver = new UserObserver(user,mailSender);
        quizNotifier.registerObserver(userObserver);

        return "User Registered Successfully";
    }

    public String loginUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent() && encoder.matches(password, user.get().getPassword())) {
            return "logged in successfully";
        }
        return "Wrong email or password";
    }

    public float getBestScore(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return user.get().getMechanicalBestScore() + user.get().getNonverbalBestScore() + user.get().getVerbalBestScore() + user.get().getNumericalBestScore();
        }
        return 0;
    }
}
