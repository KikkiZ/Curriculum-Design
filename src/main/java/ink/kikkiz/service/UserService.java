package ink.kikkiz.service;

import ink.kikkiz.dao.impl.UserDaoImpl;
import ink.kikkiz.entity.user.User;
import ink.kikkiz.entity.user.UserGrade;

import java.util.TreeMap;

/**
 * @author KikkiZ
 */
public class UserService {
    private UserDaoImpl daoImpl = new UserDaoImpl();

    /**
     * 创建用户
     *
     * @param value 用户实体
     * @return 创建是否成功，成功则返回true，否则返回false
     */
    public boolean creat(Object value) {
        return daoImpl.creat(value);
    }

    /**
     * 通过id查找用户
     *
     * @param key 用户id
     * @return 用户实体
     */
    public Object findById(Object key) {
        return daoImpl.findById(key);
    }

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 用户密码
     * @return 用户实体
     */
    public User login(String username, String password) {
        return daoImpl.findUser(username, password);
    }

    /**
     * 用户名和密码是否存在且对应
     *
     * @param username 用户名
     * @param password 用户密码
     * @return 返回是否是否存在该记录，存在则返回true，否则返回false
     */
    public boolean isUserExist(String username, String password) {
        return daoImpl.isUserExist(username, password);
    }

    /**
     * 判断用户名是否已被占用
     *
     * @param username 用户名
     * @return 返回是否被占用，未占用返回true，否则返回false
     */
    public boolean isUserExist(String username) {
        return daoImpl.isUserExist(username);
    }

    /**
     * 返回指定id对应的用户的答题历史记录
     *
     * @param id 用户id
     * @return 答题记录是TreeMap集合
     */
    public TreeMap<Integer, UserGrade> getGrades(int id) {
        return daoImpl.findGradesById(id);
    }

    /**
     * 更新指定id对应的用户的答题历史记录
     *
     * @param id 用户id
     * @param data 答题记录的TreeMap集合
     * @return 返回是否更新成功，更新成功则返回true，否则返回false
     */
    public boolean updateGrades(int id, TreeMap data) {
        return daoImpl.updateGrades(id, data);
    }
}
