package model;


import java.util.*;

public class Question extends TextModel {

    private Boolean isSuccess = true;
    private List<Answer> answers;

    public Question() {
    }

    public Question(Integer number, String text, Boolean isSuccess, List<Answer> answers) {
        super(number, text);
        this.isSuccess = isSuccess;
        this.answers = answers;
    }

    public Question(String text, Boolean isSuccess, List<Answer> answers) {
        super(text);
        this.isSuccess = isSuccess;
        this.answers = answers;
    }

    public Question(Integer number, String text, Boolean isSuccess) {
        super(number, text);
        this.isSuccess = isSuccess;
    }

    public Question(Integer number, String text) {
        super(number, text);
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
