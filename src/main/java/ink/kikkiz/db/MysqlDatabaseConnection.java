package ink.kikkiz.db;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

/**
 * @author KikkiZ
 */
public class MysqlDatabaseConnection implements DatabaseConnection {
    private final String DRIVER_CLASS;
    private final String DATABASE_URL;
    private final String DATABASE_USER;
    private final String DATABASE_PWD;

    private Connection connection;

    public MysqlDatabaseConnection() throws Exception {
        //读取配置
        Properties properties = new Properties();
        properties.load(new InputStreamReader(new FileInputStream("src/main/resources/config.properties")));

        //获取具体配置信息
        DRIVER_CLASS = properties.getProperty("DRIVER_CLASS");
        DATABASE_URL = properties.getProperty("DATABASE_URL");
        DATABASE_USER = properties.getProperty("DATABASE_USER");
        DATABASE_PWD = properties.getProperty("DATABASE_PWD");

        //创建连接对象
        Class.forName(DRIVER_CLASS);
        connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PWD);
    }

    @Override
    public Connection getConnection() {
        if (connection == null) {
            try {
                //创建连接对象
                Class.forName(DRIVER_CLASS);
                connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PWD);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return connection;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
