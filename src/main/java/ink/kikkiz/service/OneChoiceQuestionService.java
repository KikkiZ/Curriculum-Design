package ink.kikkiz.service;

import ink.kikkiz.dao.impl.OneChoiceQuestionDaoImpl;
import ink.kikkiz.entity.question.OneChoiceQuestion;

import java.util.LinkedHashSet;
import java.util.TreeMap;

/**
 * @author KikkiZ
 */
public class OneChoiceQuestionService implements QuestionService {
    private final OneChoiceQuestionDaoImpl daoImpl = new OneChoiceQuestionDaoImpl();

    /**
     * 获取单选题列表
     *
     * @param ids 随机单选题题号
     * @return 存有单选题的TreeMap集合
     */
    @Override
    public TreeMap<Integer, OneChoiceQuestion> getQuestionList(LinkedHashSet ids) {
        return daoImpl.getQuestionList(ids);
    }

    /**
     * 获取单选题总题数
     *
     * @return 单选题总题数
     */
    @Override
    public int getQuestionCount() {
        return daoImpl.getQuestionCount();
    }

    /**
     * 增加一个单选题
     *
     * @param value 单选题实体类
     * @return 是否增加成功，增加成功则返回true，否则返回false
     */
    @Override
    public boolean creat(Object value) {
        return daoImpl.creat(value);
    }

    /**
     * 查找指定id的单选题
     *
     * @param key 单选题id
     * @return 返回单选题实体
     */
    @Override
    public Object findQuestion(Object key) {
        return daoImpl.findById(key);
    }
}

