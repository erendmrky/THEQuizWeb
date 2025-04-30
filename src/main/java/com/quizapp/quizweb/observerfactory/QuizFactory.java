package com.quizapp.quizweb.observerfactory;

import com.quizapp.quizweb.model.Question;
import com.quizapp.quizweb.model.Quiz;

import java.util.List;

public class QuizFactory {

    public static Quiz createQuiz(String topicName, List<Question> questionList) {
        Quiz quiz = new Quiz();
        quiz.setTopic(topicName);

        for (Question question : questionList) {
            question.setQuiz(quiz);
        }
        quiz.setQuestions(questionList);
        return quiz;
    }
}
