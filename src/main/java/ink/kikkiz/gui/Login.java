package ink.kikkiz.gui;

import ink.kikkiz.client.Client;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * @author KikkiZ
 */
public class Login implements IScene {
    @Override
    public Scene getScene() {
        TextField account = new TextField();
        account.setMinSize(200, 30);
        account.setMaxSize(200, 30);
        account.setLayoutX(130);
        account.setLayoutY(50);

        PasswordField password = new PasswordField();
        password.setMinSize(200, 30);
        password.setMaxSize(200, 30);
        password.setLayoutX(130);
        password.setLayoutY(100);

        Button login = new Button("登录");
        login.setStyle("-fx-font-size: 14px");
        login.setMinSize(120, 40);
        login.setMaxSize(120, 40);
        login.setLayoutX(70);
        login.setLayoutY(160);

        Button register = new Button("注册");
        register.setStyle("-fx-font-size: 14px");
        register.setMinSize(120, 40);
        register.setMaxSize(120, 40);
        register.setLayoutX(210);
        register.setLayoutY(160);

        Text accountText = new Text("用 户 名");
        accountText.setStyle("-fx-font-size: 14px;");
        accountText.setLayoutX(70);
        accountText.setLayoutY(69);

        Text passwordText = new Text("密     码");
        passwordText.setStyle("-fx-font-size: 14px;");
        passwordText.setLayoutX(70);
        passwordText.setLayoutY(119);

        Text caution = new Text();
        caution.setStyle("-fx-font-size: 14px;");
        caution.setFill(Color.RED);
        caution.setLayoutX(70);
        caution.setLayoutY(149);

        login.setOnAction(event -> {
            if (account.getText().equals("")) {
                caution.setText("请输入用户名");
            } else if (password.getText().equals("")) {
                caution.setText("请输入密码");
            } else if(Client.getUserService().isUserExist(account.getText(), password.getText())) {
                //存储使用程序的用户
                Client.setUser(Client.getUserService().login(account.getText(), password.getText()));
                //获取使用程序的用户的历史成绩
                Client.getUser().setGrades(Client.getUserService().getGrades(Client.getUser().getId()));
                //for (Integer key : Client.getUser().getGrades().keySet()) {
                //    System.out.println(Client.getUser().getGrades().get(key));
                //}
                Client.getLogger().info("用户[" + Client.getUser().getName() + "]登录成功");
                //此处应进入其他场景
                Client.getStage().setScene(new Main().getScene());
            } else {
                caution.setText("登录失败，请输入正确的用户名或密码");
            }
        });

        register.setOnAction(event -> {
            //跳转到注册页面
            Client.getStage().setTitle("注册");
            Client.getStage().setScene(new Register().getScene());
        });

        Pane pane = new Pane(account, password, login, register, accountText, passwordText, caution);
        pane.setPrefSize(400, 250);

        return new Scene(pane);
    }
}
