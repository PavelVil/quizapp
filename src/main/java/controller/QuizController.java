package controller;


import java.io.*;
import java.util.*;

import model.*;
import service.*;

public interface QuizController {

    void buildQuiz();

    List<Question> startQuiz(BufferedReader reader);

    void quizResult(List<Question> questions);

    Integer getRightAnswersCount(List<Question> questions);

    void startAnswering(List<Question> questions, BufferedReader reader);

    void printAnswers(Question question);

    void setSuccessForQuestion(Question question, String[] answers);

    ReaderService getReaderService();

}
