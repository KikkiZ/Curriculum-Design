package ink.kikkiz.dao.impl;

import ink.kikkiz.client.Client;
import ink.kikkiz.dao.UserDao;
import ink.kikkiz.entity.user.User;
import ink.kikkiz.entity.user.UserGrade;

import java.sql.*;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author KikkiZ
 */
public class UserDaoImpl implements UserDao {
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public UserDaoImpl() {
        //获取连接
        connection = Client.getConnection();
        try {
            //获取基本操作
            statement = connection.createStatement();
        } catch (SQLException e) {
            Client.getLogger().warn("获取statement对象时出现异常");
        }
    }

    /**
     * 实现用户数据的增加操作
     *
     * @param value 需要添加到数据库中的User信息
     * @return 数据成功添加则返回true，否则返回false
     */
    @Override
    public boolean creat(Object value) {
        try {
            //判断需要新建的对象是否为对应的实体类
            if (!(value instanceof User)) {
                return false;
            } else {
                //将实体类转换成符合sql语句的字符串
                String sql = "insert into users_data values(" + userToString((User)value) + ")";
                if (statement.executeUpdate(sql) != 0) {
                    Client.getLogger().info("用户[" + ((User) value).getName() +"]注册成功");
                    return true;
                }
            }
        } catch (SQLException e) {
            Client.getLogger().warn("添加用户时发生异常");
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
     * 查找指定id的用户的数据信息
     *
     * @param key 需要查找的id
     * @return 根据查询到的信息，将其封装为具体的User实体类
     */
    @Override
    public Object findById(Object key) {
        String sql = "select * from users_data where id = " + key;
        User user = null;
        try {
            //获取sql语句的结果集
            resultSet = statement.executeQuery(sql);
            //判断集合是否为空，如果不为空，则将查询到的数据包装成对象，为空则将返回null
            if (resultSet.next()) {
                user = packagingUser(resultSet);
            } else {
                Client.getLogger().warn("查找不到id为" + key + "的对象");
            }
        } catch (SQLException e) {
            Client.getLogger().warn("查询数据出现异常");
        }
        return user;
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

    /**
     * 通过用户名和密码找到用户
     *
     * @param username 用户名
     * @param password 密码
     * @return 该用户存在则返回该用户的实体类，否则返回null
     */
    @Override
    public User findUser(String username, String password) {
        String sql = "select * from users_data where username = ? and password = ?";
        try {
            //防止通过特殊手段，在仅知道用户名的情况下跳过密码
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            //resultSet = statement.executeQuery(sql);
            //判断resultSet是否为空，如果为空则返回false，登录失败，如果不为空则返回true，登录成功
            if (resultSet.next()) {
                return packagingUser(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            Client.getLogger().warn("登录时发生异常");
            return null;
        }
    }

    /**
     * 判断是否存在该用户
     *
     * @param username 用户名
     * @param password 密码
     * @return 存在该用户则返回true，否则返回false
     */
    @Override
    public boolean isUserExist(String username, String password) {
        String sql = "select * from users_data where username = ? and password = ?";
        try {
            //防止通过特殊手段，在仅知道用户名的情况下跳过密码
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            //resultSet = statement.executeQuery(sql);
            //判断resultSet是否为空，如果为空则返回false，登录失败，如果不为空则返回true，登录成功
            return resultSet.next();
        } catch (SQLException e) {
            Client.getLogger().warn("登录时发生异常");
            return false;
        }
    }

    /**
     * 判断是否存在该用户
     *
     * @param username 用户名
     * @return 存在该用户则返回true，否则返回false
     */
    @Override
    public boolean isUserExist(String username) {
        String sql = "select * from users_data where username = '" + username + "'";
        try {
            resultSet = statement.executeQuery(sql);
            //判断resultSet是否为空，如果为空则返回true，该用户名不存在，如果不为空则返回false，该用户名已存在
            return !resultSet.next();
        } catch (SQLException e) {
            Client.getLogger().warn("查找用户名为" + username + "时发生异常");
            return false;
        }
    }

    /**
     * 通过id从数据库获取指定的用户答题的历史成绩
     *
     * @param id 用户的id
     * @return 用户答题的历史成绩
     */
    @Override
    public TreeMap<Integer, UserGrade> findGradesById(int id) {
        String sql = "select * from records where id = " + id;
        TreeMap<Integer, UserGrade> map = null;
        try {
            //获取指定id用户的所有答题记录，并将其包装成map集合
            //当该用户无答题记录时，将返回一个空的map集合
            resultSet = statement.executeQuery(sql);
            map = packagingUserGrades(resultSet);
        } catch (SQLException e) {
            Client.getLogger().warn("获取id为" + id + "的用户的答题记录时出现异常");
        }
        return map;
    }

    /**
     * 更新数据库中用户答题的记录
     *
     * @param id   用户的id
     * @param data 用户的答题记录
     * @return 如果更新成功则返回true，否则返回false
     */
    @Override
    public boolean updateGrades(int id, TreeMap data) {
        for (int i = 1; i <= data.size(); i++) {
            String sql = "select * from records where id = " + id + " and times = " + ((UserGrade)data.get(i)).getTimes();
            try {
                //尝试依次查询用户的答题记录，当查询不到记录时，将上传记录
                if (!statement.executeQuery(sql).next()) {
                    String insertSql = "insert into records values(" + userGradeToString((UserGrade) data.get(i)) + ")";
                    statement.executeUpdate(insertSql);
                }
            } catch (SQLException e) {
                Client.getLogger().warn("给id为" + id + "的用户更新成绩时出现问题");
                return false;
            }
        }
        return true;
    }

    //将User实体转换成符合sql语法的字符串
    private String userToString(User user) {
        return "null,'" + user.getName() + "','" + user.getPassword() + "'";
    }

    //将UserGrade实体转换成符合sql语法的字符串
    private String userGradeToString(UserGrade grade) {
        return grade.getId() + ", " + grade.getTimes() + ", " + grade.getGrade() + ", " + grade.getCorrectNumber() +
                ", " + grade.getQuestionNumber() + ", " + grade.getOneChoiceNumber() + ", " + grade.getMultipleChoiceNumber();
    }

    //将resultSet封装成User实体
    private User packagingUser(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getInt("id") ,resultSet.getString("username"), resultSet.getString("password"));
    }

    //将resultSet封装成UserGrade实体，然后存入一个map集合
    private TreeMap<Integer, UserGrade> packagingUserGrades(ResultSet resultSet) throws SQLException {
        TreeMap<Integer, UserGrade> map = new TreeMap<>();
        while (resultSet.next()) {
            UserGrade grade = new UserGrade(resultSet.getInt("id"), resultSet.getInt("times"),
                    resultSet.getFloat("grade"), resultSet.getInt("correct_number"),
                    resultSet.getInt("question_number"), resultSet.getInt("one_choice_number"),
                    resultSet.getInt("multiple_choice_number"));
            map.put(grade.getTimes(), grade);
        }
        return map;
    }
}
