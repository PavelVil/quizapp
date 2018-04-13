package controller;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

import model.*;
import org.junit.*;
import org.junit.rules.*;
import service.*;

import static org.junit.Assert.*;

import static resources.Constance.PATH_TO_TEST_FILE;

public class QuizControllerTest {

    private QuizController controller;

    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void setUp() {
        controller = new XmlQuizController(new XmlReaderService(), new XmlWriteService());
    }

    @Test
    public void testSetSuccessForQuestions() throws Exception {
        List<Question> questions = controller.getReaderService().readQuestions(PATH_TO_TEST_FILE);
        assertEquals(4, questions.stream().filter(s -> s.getSuccess().equals(true)).count());

        String[] answers = {"1"};
        controller.setSuccessForQuestion(questions.get(1), answers);

        assertNotEquals(4, questions.stream().filter(s -> s.getSuccess().equals(true)).count());
    }

    @Test
    public void testStartAnswering() throws Exception {
        List<Question> questions = controller.getReaderService().readQuestions(PATH_TO_TEST_FILE);
        List<String> answersList = Arrays.asList("1", "2", "3", "4");
        File tmpFile = temporaryFolder.newFile("tmp.txt");
        Files.write(Paths.get(tmpFile.getPath()), answersList, Charset.forName("UTF-8"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(tmpFile)));
        assertEquals(4, questions.stream().filter(s -> s.getSuccess().equals(true)).count());

        controller.startAnswering(questions, reader);
        assertEquals(2, questions.stream().filter(s -> s.getSuccess().equals(true)).count());
        reader.close();
    }

}
