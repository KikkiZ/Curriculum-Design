package ink.kikkiz.dao;

import ink.kikkiz.entity.user.UserGrade;

import java.util.TreeMap;

/**
 * @author KikkiZ
 */
public interface UserDao<Integer, User> extends BaseDao {
    /**
     * 通过用户名和密码找到用户
     *
     * @param username 用户名
     * @param password 密码
     * @return 该用户存在则返回该用户的实体类，否则返回null
     */
    User findUser(String username, String password);

    /**
     * 判断是否存在该用户
     *
     * @param username 用户名
     * @param password 密码
     * @return 存在该用户则返回true，否则返回false
     */
    boolean isUserExist(String username, String password);

    /**
     * 判断是否存在该用户
     *
     * @param username 用户名
     * @return 存在该用户则返回true，否则返回false
     */
    boolean isUserExist(String username);

    /**
     * 通过id从数据库获取指定的用户答题的历史成绩
     *
     * @param id 用户的id
     * @return 用户答题的历史成绩
     */
    TreeMap<Integer, UserGrade> findGradesById(int id);

    /**
     * 更新数据库中用户答题的记录
     *
     * @param id 用户的id
     * @param data 用户的答题记录
     * @return 如果更新成功则返回true，否则返回false
     */
    boolean updateGrades(int id, TreeMap<Integer, UserGrade> data);
}
