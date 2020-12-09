package ink.kikkiz.gui;

import ink.kikkiz.client.Client;
import ink.kikkiz.entity.user.User;
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
public class Register implements IScene {
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

        PasswordField repassword = new PasswordField();
        repassword.setMinSize(200, 30);
        repassword.setMaxSize(200, 30);
        repassword.setLayoutX(130);
        repassword.setLayoutY(150);

        Button register = new Button("注册");
        register.setStyle("-fx-font-size: 14px");
        register.setMinSize(120, 40);
        register.setMaxSize(120, 40);
        register.setLayoutX(70);
        register.setLayoutY(210);

        Button cancel = new Button("取消");
        cancel.setStyle("-fx-font-size: 14px");
        cancel.setMinSize(120, 40);
        cancel.setMaxSize(120, 40);
        cancel.setLayoutX(210);
        cancel.setLayoutY(210);

        Text accountText = new Text("用 户 名");
        accountText.setStyle("-fx-font-size: 14px;");
        accountText.setLayoutX(70);
        accountText.setLayoutY(69);

        Text passwordText = new Text("密     码");
        passwordText.setStyle("-fx-font-size: 14px;");
        passwordText.setLayoutX(70);
        passwordText.setLayoutY(119);

        Text repasswordText = new Text("再次输入");
        repasswordText.setStyle("-fx-font-size: 14px;");
        repasswordText.setLayoutX(70);
        repasswordText.setLayoutY(169);

        Text caution = new Text();
        caution.setStyle("-fx-font-size: 14px;");
        caution.setFill(Color.RED);
        caution.setLayoutX(70);
        caution.setLayoutY(199);

        register.setOnAction(event -> {
            if (account.getText().equals("")) {
                caution.setText("请输入用户名");
            } else if (password.getText().equals("")) {
                caution.setText("请输入密码");
            } else if (repassword.getText().equals("")) {
                caution.setText("请再次输入密码");
            } else if (!Client.getUserService().isUserExist(account.getText())) {
                caution.setText("该用户名已被占用");
            } else if (!password.getText().equals(repassword.getText())) {
                caution.setText("两次密码输入不一致");
            } else if (Client.getUserService().creat(new User(account.getText(), password.getText()))) {
                caution.setText("注册成功");
            } else {
                caution.setText("注册时发生了未知的意外");
                Client.getLogger().warn("注册时发生了未知的异常");
            }
        });

        cancel.setOnAction(event -> {
            //跳转回登录页面
            Client.getStage().setTitle("登录");
            Client.getStage().setScene(new Login().getScene());
        });

        Pane pane = new Pane(account, password, repassword, register, cancel, accountText, passwordText, repasswordText, caution);
        pane.setPrefSize(400, 300);

        return new Scene(pane);
    }
}