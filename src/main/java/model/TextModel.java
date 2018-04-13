package model;


public class TextModel extends BaseModel {

    private String text;

    public TextModel() {
    }

    public TextModel(Integer number, String text) {
        super(number);
        this.text = text;
    }

    public TextModel(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
