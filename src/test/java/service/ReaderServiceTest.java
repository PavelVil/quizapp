package service;


import java.io.*;
import java.util.*;

import model.*;
import org.junit.*;

import static org.junit.Assert.*;
import static resources.Constance.PATH_TO_TEST_FILE;

public class ReaderServiceTest {

    private ReaderService readerService;

    @Before
    public void setUp() {
        readerService = new XmlReaderService();
    }

    @Test
    public void testGetStandardFile() throws Exception {
        File file = readerService.getFile(null);

        assertTrue(file.exists());
    }

    @Test
    public void testGetFileWithNotExistPath() throws Exception {
        File file = readerService.getFile("not exist path");

        assertTrue(file.exists());
    }

    @Test
    public void testReadQuestions() throws Exception {
        List<Question> questions = readerService.readQuestions(PATH_TO_TEST_FILE);

        assertNotNull(questions);
        assertEquals(4, questions.size());
    }

    @Test
    public void testReadAnswersFromQuestions() throws Exception {
        List<Question> questions = readerService.readQuestions(PATH_TO_TEST_FILE);

        assertNotNull(questions.get(0).getAnswers());
        assertEquals(2, questions.get(0).getAnswers().size());
    }

}
