package Lab5.com.company;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class WarningForm extends Application {
    private static AnchorPane pane = new AnchorPane();
    private static HBox hBox = new HBox();
    private static Scene scene = new Scene(pane, 300, 80);

    private static Label label = new Label("This username is already in the system");

    public WarningForm() {
        add();
        settings();
    }



    @Override
    public void start(Stage stage) {
        stage.setTitle("Warning!!");
        stage.setScene(scene);
        stage.show();
    }

    private void settings() {
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(10, 10, 10, 10));
        pane.setPadding(new Insets(10, 10, 10, 10));

    }
    private void add() {
        hBox.getChildren().clear();
        pane.getChildren().clear();
        hBox.getChildren().addAll(label);
        pane.getChildren().addAll(hBox);
    }
}
