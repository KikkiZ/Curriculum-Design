package ink.kikkiz.gui.factory;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * @author KikkiZ
 */
public class ButtonFactory implements ControlFactory<Button> {
    //宽
    private final int width = 40;
    //高
    private final int height = 50;
    /**
     * 获取指定产品
     *
     * @return 特定产品
     */
    @Override
    public Button newProduct() {
        return commonButton("");
    }

    public Button commonButton(String text) {
        Button button = new Button(text);
        button.setMinSize(width, height);
        button.setMaxSize(width, height);
        button.setFont(new Font(14));
        button.getStyleClass().add("common-button");
        //button.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #cccccc; -fx-border-radius: 8px; -fx-background-radius: 10px");

        return button;
    }

    public Button trueButton(String text) {
        Button button = new Button(text);
        button.setMinSize(width, height);
        button.setMaxSize(width, height);
        button.setFont(new Font(14));
        button.setTextFill(Color.WHITE);
        button.getStyleClass().add("true-button");
        //button.setStyle("-fx-background-color: #5cb85c; -fx-border-color: #4cae4c; -fx-border-radius: 8px; -fx-background-radius: 10px");

        return button;
    }

    public Button wrongButton(String text) {
        Button button = new Button(text);
        button.setMinSize(width, height);
        button.setMaxSize(width, height);
        button.setFont(new Font(14));
        button.setTextFill(Color.WHITE);
        button.getStyleClass().add("wrong-button");
        //button.setStyle("-fx-background-color: #d9534f; -fx-border-color: #d43f3a; -fx-border-radius: 8px; -fx-background-radius: 10px ");

        return button;
    }
}
