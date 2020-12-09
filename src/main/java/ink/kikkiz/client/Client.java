package ink.kikkiz.client;

import ink.kikkiz.db.MysqlDatabaseConnection;
import ink.kikkiz.entity.question.TestQuestions;
import ink.kikkiz.entity.user.User;
import ink.kikkiz.gui.Login;
import ink.kikkiz.log.Logger;
import ink.kikkiz.service.MultipleChoiceQuestionService;
import ink.kikkiz.service.OneChoiceQuestionService;
import ink.kikkiz.service.UserService;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.sql.Connection;
import java.util.TreeMap;

/**
 * @author KikkiZ
 */
public class Client extends Application {
    private static Logger logger;
    private static MysqlDatabaseConnection connection;
    private static Stage stage;
    private static User user;
    private static TestQuestions test;
    private static MultipleChoiceQuestionService multiService;
    private static OneChoiceQuestionService oneService;
    private static UserService userService;

    public static void main(String[] args) {
        logger = new Logger();

        try {
            connection = new MysqlDatabaseConnection();
        } catch (Exception e) {
            logger.warn("连接获取异常");
            logger.warn(e);
        }
        logger.info("数据库连接成功");

        multiService = new MultipleChoiceQuestionService();
        oneService = new OneChoiceQuestionService();
        userService = new UserService();
        logger.info("service创建成功");

        //System.out.println(new UserDaoImpl().isUserExist("admin' or '1=1"));
        logger.info("程序成功启动");
        launch(args);

        //new Test().test();
    }

    @Override
    public void start(Stage stage) {
        Client.stage = stage;

        stage.getIcons().add(new Image("file:src/main/resources/image/icon.png"));
        stage.setTitle("登录");
        stage.setResizable(false);
        stage.setScene(new Login().getScene());

        stage.setOnCloseRequest(windowEvent -> close());

        stage.show();
    }

    public static Logger getLogger() {
        return logger;
    }

    public static Connection getConnection() {
        return connection.getConnection();
    }

    public static Stage getStage() {
        return stage;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Client.user = user;
    }

    public static void setTestQuestions(TestQuestions test) {
        Client.test = test;
    }

    public static TestQuestions getTestQuestions() {
        return test;
    }

    public static MultipleChoiceQuestionService getMultiService() {
        return multiService;
    }

    public static OneChoiceQuestionService getOneService() {
        return oneService;
    }

    public static UserService getUserService() {
        return userService;
    }

    public static void close() {
        if (user != null && userService.updateGrades(user.getId(), (TreeMap)user.getGrades())) {
            logger.info("用户["+ user.getName() +"]成绩上传成功");
            logger.info("用户[" + user.getName() + "]登出成功");
        } else if (user != null){
            logger.warn("上传成绩时发生异常");
        }

        connection.close();
        logger.info("切断数据库连接");
        logger.info("程序成功关闭");

        logger.close();
    }
}
