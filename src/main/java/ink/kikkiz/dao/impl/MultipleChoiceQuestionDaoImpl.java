package ink.kikkiz.dao.impl;

import ink.kikkiz.client.Client;
import ink.kikkiz.dao.QuestionDao;
import ink.kikkiz.entity.question.Choice;
import ink.kikkiz.entity.question.MultipleChoiceQuestion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * @author KikkiZ
 */
public class MultipleChoiceQuestionDaoImpl implements QuestionDao {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public MultipleChoiceQuestionDaoImpl() {
        //获取连接
        this.connection = Client.getConnection();

        //获取基本操作
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            Client.getLogger().warn("获取statement对象时出现异常");
        }
    }

    /**
     * 获取指定id列表的题目
     *
     * @param ids 题目的id列表
     * @return 返回题目集合
     */
    @Override
    public TreeMap<Integer, MultipleChoiceQuestion> getQuestionList(LinkedHashSet ids) {
        TreeMap<Integer, MultipleChoiceQuestion> map = new TreeMap<>();
        //题号
        int key = 1;
        //依次获取题目，将题目和题号放入map集合中
        for (Object id : ids) {
            map.put(key++, (MultipleChoiceQuestion) findById(id));
        }

        return map;
    }

    /**
     * 获取总题目的数量
     *
     * @return 总题数
     */
    @Override
    public int getQuestionCount() {
        String sql = "select count(*) as num from multiple_choice_question";
        int number = 0;
        try {
            resultSet = statement.executeQuery(sql);
            if(resultSet.next()) {
                number = resultSet.getInt("num");
            }
        } catch (SQLException e) {
            Client.getLogger().warn("查询多选题总题数时发生异常");
        }
        return number;
    }

    /**
     * 实现数据的增加操作
     *
     * @param value 需要添加到数据库中的对象
     * @return 数据成功添加则返回true，否则返回false
     */
    @Override
    public boolean creat(Object value) {
        try {
            //判断需要新建的对象是否为对应的实体类
            if (!(value instanceof MultipleChoiceQuestion)) {
                return false;
            } else {
                //将实体类转换成符合sql语句的字符串
                String sql = "insert into multiple_choice_question values(" + questionToString((MultipleChoiceQuestion) value) + ")";
                if (statement.executeUpdate(sql) != 0) {
                    Client.getLogger().info("添加多选题成功");
                }
            }
        } catch (SQLException e) {
            Client.getLogger().warn("添加多选题时发生异常");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 实现数据的修改操作
     *
     * @param value 包含了修改数据的信息，该对象中需要提供id
     * @return 数据成功修改则返回true，否则返回false
     */
    @Deprecated
    @Override
    public boolean update(Object value) {
        return false;
    }

    /**
     * 执行移除操作
     *
     * @param key 指定需要移除数据的id
     * @return 数据成功移除则返回true，否则返回false
     */
    @Deprecated
    @Override
    public boolean remove(Object key) {
        return false;
    }

    /**
     * 执行批量移除操作
     *
     * @param keys 指定一批需要移除数据的id
     * @return 数据成功移除则返回true，否则返回false
     */
    @Deprecated
    @Override
    public boolean removeBatch(Set keys) {
        return false;
    }

    /**
     * 查找指定id的数据信息
     *
     * @param key 需要查找的id
     * @return 根据查询到的信息，将其封装为具体实体类
     */
    @Override
    public Object findById(Object key) {
        String sql = "select * from multiple_choice_question where id = " + key;
        MultipleChoiceQuestion question = null;
        try {
            resultSet = statement.executeQuery(sql);
            //判断集合是否为空，如果不为空，则将查询到的数据包装成对象，为空则将返回null
            if (resultSet.next()) {
                question = packaging(resultSet);
            }
        } catch (SQLException e) {
            Client.getLogger().warn("获取id为" + key + "的多选题时出现异常");
            e.printStackTrace();
        }
        return question;
    }

    /**
     * 查找所有的数据信息
     *
     * @return 将所有查找到的数据信息封装为实体类，然后返回map集合
     */
    @Deprecated
    @Override
    public Map findAll() {
        return null;
    }

    //多选题实体包装方法
    private MultipleChoiceQuestion packaging(ResultSet resultSet) throws SQLException {
        MultipleChoiceQuestion question = new MultipleChoiceQuestion();
        question.setId(resultSet.getInt("id"));
        question.setQuestion(resultSet.getString("question"));
        question.setChoices(new String[]{
                resultSet.getString("choice_a"),
                resultSet.getString("choice_b"),
                resultSet.getString("choice_c"),
                resultSet.getString("choice_d")});
        question.setRightAnswers(resultSet.getString("answers"));
        return question;
    }

    //将多选题实体转换成符合sql语法的字符串
    private String questionToString(MultipleChoiceQuestion question) {
        StringBuilder answers = new StringBuilder();
        for (Choice answer : question.getRightAnswers()) {
            answers.append(answer.toString());
        }
        return "null,'" + question.getQuestion() + "',"
                + stringArray(question.getChoices()) + ",'"
                + answers.toString() +"'";
    }

    //将选项数组转换成字符串
    private  String  stringArray(String[] array) {
        return "'" + array[0] + "','" + array[1] + "','" + array[2] + "','" + array[3] + "'";
    }
}
