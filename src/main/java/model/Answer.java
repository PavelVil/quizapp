package model;


public class Answer extends TextModel {

    private Boolean right = false;

    public Answer() {
    }

    public Answer(Integer number, String text, Boolean right) {
        super(number, text);
        this.right = right;
    }

    public Answer(String text, Boolean right) {
        super(text);
        this.right = right;
    }

    public Boolean getRight() {
        return right;
    }

    public void setRight(Boolean right) {
        this.right = right;
    }
}
