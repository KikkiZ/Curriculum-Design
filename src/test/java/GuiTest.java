import ink.kikkiz.gui.Main;
import ink.kikkiz.gui.factory.ButtonFactory;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;


public class GuiTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        new Main().addQuestionMethod();

        stage.show();
    }
}
