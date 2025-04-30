package com.quizapp.quizweb.observerfactory;

public interface Subject {
    public void registerObserver(Observer observer);
    public void removeObserver(Observer observer);
    public void notifyObservers(String topicName);
}
