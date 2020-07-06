package Lab5.com.company;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class formForReceivingType extends Application {
    private static TicketType type;
    private static AnchorPane pane = new AnchorPane();
    private static Button input = new Button("Input");
    private static HBox hBox = new HBox();
    private static Scene scene = new Scene(pane, 300, 80);
    private static Label label = new Label("Выберите тип:");
    private static ComboBox comboBox = new ComboBox(FXCollections.observableArrayList("Null", "Cheap", "Budgetary", "Usual", "Vip"));

    public formForReceivingType() {
        add();
        settings();
    }

    public TicketType getType() {
        return type;
    }

    @Override
    public void start(Stage stage) {
        stage.setScene(scene);
        stage.show();
    }

    private void settings() {
        comboBox.setValue("Null");
        input.setPrefWidth(150);
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(10, 10, 10, 10));
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setTopAnchor(input, 35d);
        pane.setLeftAnchor(input, 60d);
        input.setOnAction(event -> {
            if (comboBox.getValue().toString().equalsIgnoreCase("null")) type = null;
            else {
                type = TicketType.valueOf(comboBox.getValue().toString().toUpperCase());
            }
            System.out.println("recType");
            System.out.println(type);
            CommandList.setParam(type);
            Stage stage = (Stage) input.getScene().getWindow();
            stage.close();
        });
    }

    private void add() {

        pane.getChildren().clear();
        hBox.getChildren().clear();
        hBox.getChildren().addAll(label, comboBox);
        pane.getChildren().addAll(hBox, input);
    }

    public static void setText() {
        input = I18N.buttonForKey("button.sendCom");
        label.setText(I18N.textForKey("text.inputtype"));

    }
}
