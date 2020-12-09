package ink.kikkiz.service;

import ink.kikkiz.entity.question.Question;

import java.util.LinkedHashSet;
import java.util.TreeMap;

/**
 * @author KikkiZ
 */
public interface QuestionService {
     TreeMap<Integer, ? extends Question> getQuestionList(LinkedHashSet ids);

     int getQuestionCount();

     boolean creat(Object value);

     Object findQuestion(Object key);
}
