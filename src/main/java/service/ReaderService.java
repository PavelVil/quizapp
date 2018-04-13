package service;


import java.io.*;
import java.nio.file.*;
import java.util.*;

import model.*;

import static service.ServiceConstance.DEFAULT_QUESTION_FILENAME;

public interface ReaderService {

    List<Question> readQuestions(String pathToFile);

    default File getFile(String path) {
        Path xmlFileDirectory = Paths.get("src", "main", "resources", DEFAULT_QUESTION_FILENAME);
        if (path == null || path.isEmpty()) {
            return new File(xmlFileDirectory.toUri());
        }
        File file = new File(path);
        if (file.exists())
            return file;
        else
            return new File(xmlFileDirectory.toUri());
    }
}
