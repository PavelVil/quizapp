package controller;


import java.io.*;
import java.util.*;

import model.*;
import service.*;

public class XmlQuizController extends AbstractQuizController {

    public XmlQuizController(ReaderService readerService, WriterService writerService) {
        super(readerService, writerService);
    }

    public List<Question> startQuiz(BufferedReader reader) {
        System.out.println("Добро пожаловать в Quiz App.\n Сейчас вам будут предложены несколько вариантов ответов" +
                " на которые вы должны ответить. Правильных ответов может быть несколько. \n" +
                "Ответы на вопрос нужно оформлять в виде '1, 2' (где цифры - номера ответов).\n" +
                "Введите путь к файлу с вопросами или нажмите ENTER чтобы использовать файл по умолчанию.");

        String pathToFile = readLineFromBufferedReader(reader);
        List<Question> questions = readerService.readQuestions(pathToFile);
        startAnswering(questions, reader);
        return questions;
    }

    public void quizResult(List<Question> questions) {
        Integer countOfRightAnswers = getRightAnswersCount(questions);
        System.out.println("Общее колличество вопросов: " + questions.size() + "\n" + "Колличество правильный ответов: " + countOfRightAnswers);
        writerService.create(questions);
    }

    public void startAnswering(List<Question> questions, BufferedReader reader) {
        String consoleLine = "";

        for (Question question : questions) {
            printAnswers(question);
            consoleLine = readLineFromBufferedReader(reader);
            String[] answers = consoleLine.split(" ");
            setSuccessForQuestion(question, answers);
        }
    }

    public void setSuccessForQuestion(Question question, String[] answers) {
        List<Answer> answerList = question.getAnswers();
        List<Answer> userAnswers = new ArrayList<>();

        if (!checkBeforeSetSuccess(answerList, answers, question)) {
            return;
        }

        for (String answer : answers) {
            Integer numberOfQuestion = Integer.parseInt(answer) - 1;

            if (answerList.size() <= numberOfQuestion || numberOfQuestion < 0) {
                setFalseCondition(question);
                continue;
            }

            if (answerList.size() >= numberOfQuestion && answerList.get(numberOfQuestion).getRight() && question.getSuccess()) {
                question.setSuccess(true);
            } else {
                question.setSuccess(false);
            }

            if (userAnswers.contains(answerList.get(numberOfQuestion))) {
                setFalseCondition(question);
                continue;
            }
            userAnswers.add(answerList.get(numberOfQuestion));
        }
        question.setAnswers(userAnswers);
    }
}
