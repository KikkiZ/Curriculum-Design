package ink.kikkiz.dao;

import java.util.LinkedHashSet;
import java.util.TreeMap;

/**
 * @author KikkiZ
 */
public interface QuestionDao<Integer, Question> extends BaseDao {
    /**
     * 获取指定id列表的题目
     *
     * @param ids 题目的id列表
     * @return 返回题目集合
     */
    TreeMap<Integer, Question> getQuestionList(LinkedHashSet<Integer> ids);

    /**
     * 获取总题目的数量
     *
     * @return 总题数
     */
    int getQuestionCount();
}
