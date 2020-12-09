package ink.kikkiz.service;

import ink.kikkiz.dao.impl.MultipleChoiceQuestionDaoImpl;
import ink.kikkiz.entity.question.MultipleChoiceQuestion;

import java.util.LinkedHashSet;
import java.util.TreeMap;

/**
 * @author KikkiZ
 */
public class MultipleChoiceQuestionService implements QuestionService {
    private final MultipleChoiceQuestionDaoImpl daoImpl = new MultipleChoiceQuestionDaoImpl();

    /**
     * 获取多选题列表
     *
     * @param ids 随机多选题题号
     * @return 返回多选题列表
     */
    @Override
    public TreeMap<Integer, MultipleChoiceQuestion> getQuestionList(LinkedHashSet ids) {
        return daoImpl.getQuestionList(ids);
    }

    /**
     * 获取多选题总题数
     *
     * @return 多选题总题数
     */
    @Override
    public int getQuestionCount() {
        return daoImpl.getQuestionCount();
    }

    /**
     * 增加一个多选题
     *
     * @param value 多选题实体类
     * @return 是否增加成功，增加成功则返回true，否则返回false
     */
    @Override
    public boolean creat(Object value) {
        return daoImpl.creat(value);
    }

    /**
     * 查找指定id的多选题
     *
     * @param key 多选题id
     * @return 返回多选题实体
     */
    @Override
    public Object findQuestion(Object key) {
        return daoImpl.findById(key);
    }
}
