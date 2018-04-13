import controller.*;
import service.*;

public class QuizApp {

    public static void main(String[] args) throws Exception {
        QuizController controller = new XmlQuizController(new XmlReaderService(), new XmlWriteService());
        controller.buildQuiz();
    }

}
