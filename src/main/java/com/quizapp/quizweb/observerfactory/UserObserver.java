package com.quizapp.quizweb.observerfactory;

import com.quizapp.quizweb.model.User;

public class UserObserver implements Observer {
    private User user;

    public UserObserver(User user) {
        this.user = user;
    }

    @Override
    public void update(String topicName) {
        System.out.println("Notifying the " + user.getUsername() + " For the new topic that had been added: " + topicName);
    }
}
