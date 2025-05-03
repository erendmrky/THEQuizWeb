package com.quizapp.quizweb.observerfactory;

import com.quizapp.quizweb.model.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class UserObserver implements Observer {
    private final User user;
    private final JavaMailSender mailSender;

    public UserObserver(User user, JavaMailSender mailSender) {
        this.user = user;
        this.mailSender = mailSender;
    }

    @Override
    public void update(String topicName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("New Questions added for topic: " + topicName);
        message.setText("Hello user "+ user.getUsername()+",\n\n" +
                "New Questions added for topic: " + topicName+" \n" +"Log in now and solve the new questions: http://localhost:8080/views/Signin_StajHazir.html"+"\n"+
                "We wish you Good Luck"+ "\n\n"+
                "Team THE");
        mailSender.send(message);
    }
}
