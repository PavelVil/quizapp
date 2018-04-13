package controller;


import java.io.*;
import java.util.*;
import java.util.regex.*;

import model.*;
import service.*;

public abstract class AbstractQuizController implements QuizController {

    protected ReaderService readerService;
    protected WriterService writerService;

    public AbstractQuizController(ReaderService readerService, WriterService writerService) {
        this.readerService = readerService;
        this.writerService = writerService;
    }

    public void buildQuiz() {
        BufferedReader reader = getBufferedReader();
        List<Question> questions = startQuiz(reader);
        quizResult(questions);
        closeBufferedReader(reader);
    }

    public Integer getRightAnswersCount(List<Question> questions) {
        Integer countOfRightAnswers = 0;
        for (Question question : questions) {
            if (question.getSuccess())
                countOfRightAnswers++;
        }
        return countOfRightAnswers;
    }

    public void printAnswers(Question question) {
        System.out.println("Вопрос: " + question.getText());
        for (int i = 0; i < question.getAnswers().size(); i++) {
            Answer answer = question.getAnswers().get(i);
            System.out.println(answer.getNumber() + ":" + answer.getText());
        }
    }

    private BufferedReader getBufferedReader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }

    String readLineFromBufferedReader(BufferedReader reader) {
        try {
            return reader.readLine();
        } catch (IOException e) {
            System.err.println("Ошибка ввода/вывода.\n" + e.getMessage());
        }
        return null;
    }

    private void closeBufferedReader(Reader reader) {
        try {
            reader.close();
        } catch (IOException e) {
            System.err.println("Ошибка ввода/вывода.\n" + e.getMessage());
        }
    }

    boolean checkCorrectInput(String[] answers) {
        for (String answer : answers) {
            Pattern pattern = Pattern.compile("[0-9]");
            Matcher matcher = pattern.matcher(answer);
            if (!matcher.matches()) {
                return false;
            }
        }
        return true;
    }

    void setFalseCondition(Question question) {
        System.err.println("Неправильный ввод! Варинат ответа засчитан не будет!");
        question.setSuccess(false);
        question.setAnswers(Collections.emptyList());
    }

    boolean checkBeforeSetSuccess(List<Answer> answerList, String[] answers, Question question) {
        Long rightAnswerCount = answerList.stream().filter(a -> a.getRight().equals(true)).count();

        if (rightAnswerCount != answers.length) {
            question.setSuccess(false);
            question.setAnswers(Collections.emptyList());
            System.err.println("Колличество вариантов ответов не совпадает с правильными.");
            return false;
        }

        if (!checkCorrectInput(answers)) {
            setFalseCondition(question);
            return false;
        }
        return true;
    }

    @Override
    public ReaderService getReaderService() {
        return readerService;
    }

}
