package service;


import java.io.*;
import java.util.*;

import model.*;
import org.junit.*;

import static org.junit.Assert.*;
import static service.ServiceConstance.RESULT_FILENAME;

public class WriterServiceTest {

    private WriterService writerService;

    @Before
    public void setUp() {
        writerService = new XmlWriteService();
    }

    @After
    public void tearDown() {
        File file = new File(RESULT_FILENAME);
        if (file.exists())
            file.delete();
    }

    @Test
    public void testCreateFileFromQuestionList() throws Exception {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question(1, "text question", true));
        createMockAnswers(questions.get(0));
        writerService.create(questions);
        File file = new File(RESULT_FILENAME);

        assertTrue(file.exists());
    }

    private void createMockAnswers(Question question) {
        List<Answer> answers = new ArrayList<>();
        answers.add(new Answer(1, "text 1", true));
        answers.add(new Answer(2, "text 2", false));
        answers.add(new Answer(3, "text 3", true));
        question.setAnswers(answers);
    }

}
