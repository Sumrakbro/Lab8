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

public class formForReceivingPerson extends Application {
    private static Stage stage;
    public static boolean b = false;
    private static Person person;
    private static AnchorPane pane = new AnchorPane();
    private static Button input = new Button("Input");
    private static HBox hBox1 = new HBox();
    private static HBox hBox2 = new HBox();
    private static Scene scene = new Scene(pane, 250, 120);
    private static Label passId_label = new Label("Введите Id:  ");
    private static Label weight_label = new Label("Введите вес:");
    private static TextField passId = new TextField();
    private static TextField weight = new TextField();

    public formForReceivingPerson() {
        add();
        settings();
    }

    public boolean getB() {
        return b;
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;


        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    private void settings() {
        input.setPrefWidth(150);
        hBox1.setSpacing(10);
        hBox2.setPadding(new Insets(1, 1, 1, 5));
        hBox2.setSpacing(10);
        hBox1.setPadding(new Insets(1, 1, 1, 5));
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setTopAnchor(hBox1, 5d);
        pane.setTopAnchor(hBox2, 40d);
        pane.setTopAnchor(input, 80d);
        pane.setLeftAnchor(input, 30d);
        pane.setRightAnchor(input, 30d);
        input.setOnAction(event -> {
            if (!(passId.getText().isEmpty() && weight.getText().isEmpty()) && passId.getText() != null && weight.getText() != null) {
                try {
                    person = new Person(Long.parseLong(weight.getText()), passId.getText());
                    sendCom();
                } catch (NumberFormatException e) {
                }

            } else {
                if (passId.getText().isEmpty()) passId.setStyle("-fx-border-color: red;");
                if (weight.getText().isEmpty()) weight.setStyle("-fx-border-color: red;");
            }
        });
        passId.setOnMousePressed(event2 -> {
            passId.setStyle("-fx-border-color: blue;");
        });
        weight.setOnMousePressed(event2 -> {
            weight.setStyle("-fx-border-color: blue;");
        });
    }

    private void sendCom() {
        System.out.println(person);
        CommandList.setParam(person);
        Stage stage = (Stage) input.getScene().getWindow();
        stage.close();
    }

    private void add() {
        hBox1.getChildren().addAll(passId_label, passId);
        hBox2.getChildren().addAll(weight_label, weight);
        pane.getChildren().addAll(hBox1, hBox2, input);
    }

    public static void setText() {
        weight_label.setText(I18N.textForKey("text.inputweight"));
        passId.setText(I18N.textForKey("text.inputid"));
        input = I18N.buttonForKey("button.sendCom");
    }

    public Person getPerson() {
        return person;
    }
}
