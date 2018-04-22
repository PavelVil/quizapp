package service;

import java.io.*;
import java.util.*;

import javax.xml.parsers.*;

import model.*;
import org.w3c.dom.*;
import org.xml.sax.*;


public class XmlReaderService implements ReaderService {

    @Override
    public List<Question> readQuestions(String pathToFile) {
        List<Question> questions = new ArrayList<>();
        Document document = buildDocument(pathToFile);
        if (Objects.nonNull(document)) {
            Element root = document.getDocumentElement();
            NodeList questionsNodeList = root.getElementsByTagName(ServiceConstance.QUESTION_ELEMENTS);
            for (int i = 0; i < questionsNodeList.getLength(); i++) {
                Question question = null;
                Node questionNode = questionsNodeList.item(i);
                if (questionNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element questionElement = (Element) questionNode;
                    question = buildQuestion(questionElement.getAttributes());
                    NodeList answersNodeList = questionElement.getElementsByTagName(ServiceConstance.ANSWER_ELEMENTS);
                    question.setAnswers(getAnswers(answersNodeList));
                }
                questions.add(question);
            }
            return questions;
        }
        return Collections.emptyList();
    }

    private Document buildDocument(String pathToFile) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            return dBuilder.parse(getFile(pathToFile));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Question buildQuestion(NamedNodeMap attributes) {
        Integer number = Integer.parseInt(attributes.getNamedItem(ServiceConstance.NUMBER_ATTRIBUTE).getTextContent());
        String text = attributes.getNamedItem(ServiceConstance.TEXT_ATTRIBUTE).getTextContent();
        return new Question(number, text);
    }

    private List<Answer> getAnswers(NodeList answersNodeList) {
        List<Answer> answers = new ArrayList<>();
        Answer answer = null;
        for (int i = 0; i < answersNodeList.getLength(); i++) {
            Node answerNode = answersNodeList.item(i);
            if (answerNode.getNodeType() == Node.ELEMENT_NODE) {
                Element answerElement = (Element) answerNode;
                answer = buildAnswer(answerElement.getAttributes());
            }
            answers.add(answer);
        }
        return answers;
    }

    private Answer buildAnswer(NamedNodeMap attributes) {
        Integer number = Integer.parseInt(attributes.getNamedItem(ServiceConstance.NUMBER_ATTRIBUTE).getTextContent());
        String text = attributes.getNamedItem(ServiceConstance.TEXT_ATTRIBUTE).getTextContent();
        Boolean isRight = Boolean.valueOf(attributes.getNamedItem(ServiceConstance.RIGHT_ATTRIBUTE).getTextContent());
        return new Answer(number, text, isRight);
    }
}
