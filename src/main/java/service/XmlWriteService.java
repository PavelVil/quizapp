package service;

import java.io.*;
import java.util.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import model.*;
import org.w3c.dom.*;


public class XmlWriteService implements WriterService {

    @Override
    public void create(List<Question> questions) {
        Document document = createDocument();
        if (Objects.nonNull(document)) {
            Element rootElement = document.createElement(ServiceConstance.QUESTIONS_ROOT_ELEMENT);
            document.appendChild(rootElement);
            for (Question question : questions) {
                Element questionElement = createQuestionElement(document, question);
                setAnswersToQuestionElement(document, question.getAnswers(), questionElement);
                rootElement.appendChild(questionElement);
            }
            createXml(document);
        }
    }

    private Document createDocument() {
        try {
            return DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    private Element createQuestionElement(Document document, Question question) {
        Element questionElement = document.createElement(ServiceConstance.QUESTION_ELEMENTS);
        questionElement.setAttribute(ServiceConstance.NUMBER_ATTRIBUTE, String.valueOf(question.getNumber()));
        questionElement.setAttribute(ServiceConstance.TEXT_ATTRIBUTE, question.getText());
        questionElement.setAttribute(ServiceConstance.SUCCESS_ATTRIBUTE, String.valueOf(question.getSuccess()));
        return questionElement;
    }

    private void setAnswersToQuestionElement(Document document, List<Answer> answers, Element questionElement) {
        for (Answer answer : answers) {
            Element answerElement = document.createElement(ServiceConstance.ANSWER_ELEMENTS);
            answerElement.setAttribute(ServiceConstance.NUMBER_ATTRIBUTE, String.valueOf(answer.getNumber()));
            answerElement.setAttribute(ServiceConstance.TEXT_ATTRIBUTE, answer.getText());
            questionElement.appendChild(answerElement);
        }
    }

    private void createXml(Document document) {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            File newFile = new File(System.getProperty("user.dir")
                    + File.separator + ServiceConstance.RESULT_FILENAME);
            StreamResult result = new StreamResult(newFile);
            transformer.transform(source, result);
            System.out.println("Файл с более подробной информацией был создан по ссылке: " + newFile.getAbsolutePath());
        } catch (TransformerException e) {
            System.err.println("Ошибка создания файла xml.\n" + e.getMessage());
        }
    }
}
