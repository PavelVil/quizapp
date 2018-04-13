import controller.*;
import service.*;

public class QuizApp {

    public static void main(String[] args) throws Exception {
        XmlQuizController controller = new XmlQuizController(new XmlReaderService(), new XmlWriteService());
        controller.buildQuiz();
    }

}
