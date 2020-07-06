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


public class formForReceivingId extends Application {
    private static long id;
    private static AnchorPane pane = new AnchorPane();
    private static Button input = new Button("Input");
    private static HBox hBox = new HBox();
    private static Scene scene = new Scene(pane, 300, 80);
    private static TextField textField = new TextField();
    private static Label label = new Label("Введите Id:");

    public formForReceivingId() {
        add();
        settings();
    }

    public long getId() {
        return id;
    }

    @Override
    public void start(Stage stage){
        stage.setScene(scene);
        stage.show();
    }

    private void settings() {
        input.setPrefWidth(150);
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(10, 10, 10, 10));
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setTopAnchor(input, 35d);
        pane.setLeftAnchor(input, 60d);
        input.setOnAction(event -> {
            try {
                if (!textField.getText().isEmpty()) {
                    try {
                        id =Long.parseLong(textField.getText());
                    }catch (NumberFormatException e){
                        textField.setStyle("-fx-border-color: red;");
                        return;
                    }
                    System.out.println("recId");
                    System.out.println(id);
                    CommandList.setParam(id);
                    Stage stage = (Stage) input.getScene().getWindow();
                    stage.close();
                } else {
                    textField.setStyle("-fx-border-color: red;");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            textField.setOnMousePressed(event2->{
                textField.setStyle("-fx-border-color: blue;");
            });
        });

    }

    private void add() {
        pane.getChildren().clear();
        hBox.getChildren().clear();
        hBox.getChildren().addAll(label, textField);
        pane.getChildren().addAll(hBox, input);
    }
    public static void setText() {
        input = I18N.buttonForKey("button.sendCom");
        label.setText(I18N.textForKey("text.inputid"));
    }
}
