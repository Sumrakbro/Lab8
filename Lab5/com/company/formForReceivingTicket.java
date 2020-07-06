package Lab5.com.company;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class formForReceivingTicket extends Application {
    private static Ticket ticket;
    private static AnchorPane pane = new AnchorPane();
    private static Button input = new Button("Input");
    private static Scene scene = new Scene(pane, 310, 250);
    private static TextField title = new TextField();
    private static TextField coordinate_X = new TextField();
    private static TextField coordinate_Y = new TextField();
    private static TextField price = new TextField();
    private static TextField weight = new TextField();
    private static TextField personId = new TextField();
    private static ComboBox comboBox = new ComboBox(FXCollections.observableArrayList("Cheap", "Budgetary", "Usual", "Vip"));
    private static Label title_label = new Label("Введите название:");
    private static Label X_label = new Label("Введите координату X:");
    private static Label Y_label = new Label("Введите координату Y:");
    private static Label price_label = new Label("Введите цену:");
    private static Label weight_label = new Label("Введите вес:");
    private static Label personId_label = new Label("Введите Id:");
    private static Label type_label = new Label("Выберите тип:");

    @Override
    public void start(Stage stage) {
        add();
        settings();
        stage.setScene(scene);
        stage.show();
    }

    private void settings() {
        pane.setTopAnchor(title_label, 10d);
        pane.setTopAnchor(X_label, 40d);
        pane.setTopAnchor(Y_label, 70d);
        pane.setTopAnchor(price_label, 100d);
        pane.setTopAnchor(weight_label, 130d);
        pane.setTopAnchor(personId_label, 160d);
        pane.setTopAnchor(type_label, 190d);
        pane.setTopAnchor(input, 220d);
        pane.setLeftAnchor(title_label, 10d);
        pane.setLeftAnchor(X_label, 10d);
        pane.setLeftAnchor(Y_label, 10d);
        pane.setLeftAnchor(price_label, 10d);
        pane.setLeftAnchor(weight_label, 10d);
        pane.setLeftAnchor(personId_label, 10d);
        pane.setLeftAnchor(type_label, 10d);
        pane.setLeftAnchor(input, 50d);
        pane.setRightAnchor(input, 50d);
        pane.setTopAnchor(title, 10d);
        pane.setTopAnchor(coordinate_X, 40d);
        pane.setTopAnchor(coordinate_Y, 70d);
        pane.setTopAnchor(price, 100d);
        pane.setTopAnchor(weight, 130d);
        pane.setTopAnchor(personId, 160d);
        pane.setTopAnchor(comboBox, 190d);
        pane.setLeftAnchor(title, 150d);
        pane.setLeftAnchor(coordinate_X, 150d);
        pane.setLeftAnchor(coordinate_Y, 150d);
        pane.setLeftAnchor(price, 150d);
        pane.setLeftAnchor(weight, 150d);
        pane.setLeftAnchor(personId, 150d);
        pane.setLeftAnchor(comboBox, 150d);
        input.setOnAction(event -> {
            if (checkFilling()&&checkData()) {
                try {
                    makeTicket();
                    sendCom();
                } catch (NumberFormatException e) {
                }
            }
        });
        title.setOnMousePressed(event2 -> {
            title.setStyle("-fx-border-color: blue;");
        });
        weight.setOnMousePressed(event2 -> {
            weight.setStyle("-fx-border-color: blue;");
        });
        personId.setOnMousePressed(event2 -> {
            personId.setStyle("-fx-border-color: blue;");
        });
        coordinate_Y.setOnMousePressed(event2 -> {
            coordinate_Y.setStyle("-fx-border-color: blue;");
        });
        coordinate_X.setOnMousePressed(event2 -> {
            coordinate_X.setStyle("-fx-border-color: blue;");
        });
        price.setOnMousePressed(event2 -> {
            price.setStyle("-fx-border-color: blue;");
        });
    }

    private void sendCom() {
        CommandList.setParam(ticket);
        PersonalArea.updateGraphic();
        PersonalArea.updateTable();
        Stage stage = (Stage) input.getScene().getWindow();
        title.clear();
        coordinate_X.clear();
        coordinate_Y.clear();
        price.clear();
        weight.clear();
        personId.clear();
        pane.getChildren().clear();
        stage.close();
    }

    private void add() {
        pane.getChildren().clear();
        pane.getChildren().addAll(title_label, title, X_label, coordinate_X,
                Y_label, coordinate_Y, price_label, price, weight_label, weight,
                personId_label, personId, type_label, comboBox, input);
    }

    private boolean checkFilling() {
        boolean b = true;
        if (title.getText().isEmpty()) {
            title.setStyle("-fx-border-color: red;");
            b = false;
        }
        if (!weight.getText().isEmpty() || !personId.getText().isEmpty()) {
            if (weight.getText().isEmpty()) {
                weight.setStyle("-fx-border-color: red;");
                b = false;
            }
            if (personId.getText().isEmpty()) {
                personId.setStyle("-fx-border-color: red;");
                b = false;
            }
        }
        if (coordinate_X.getText().isEmpty()) {
            coordinate_X.setStyle("-fx-border-color: red;");
            b = false;
        }
        if (coordinate_Y.getText().isEmpty()) {
            coordinate_Y.setStyle("-fx-border-color: red;");
            b = false;
        }
        if (price.getText().isEmpty()) {
            price.setStyle("-fx-border-color: red;");
            b = false;
        }
        return b;
    }
    private boolean checkData() {
        boolean b = true;
        try {
          makePerson();
        }catch (IllegalArgumentException e){
            b=false;
        }try {
            makePrice();
        }catch (IllegalArgumentException e){
            b=false;
        }
        try {
            makeCoor();
        }catch (IllegalArgumentException e){
            b=false;
        }
        return b;
    }
    private void makeTicket() {
        String title = this.title.getText();
        Coordinates coordinates = makeCoor();
        float price=makePrice();
        if (weight.getText() == null && personId.getText() == null) {
            if (comboBox.getValue() == null) {
                this.ticket = new Ticket(title, coordinates, price);
            } else this.ticket = new Ticket(title, coordinates, price, makeType());
        } else this.ticket = new Ticket(title, coordinates, price, makeType(), makePerson());
    }
    private Float makePrice() {
        if (price.getText().isEmpty()) return null;
        Float price;
        try {
            price = Float.parseFloat(this.price.getText());
        } catch (IllegalArgumentException e) {
            makeredborder(this.price);
            throw e;
        }
        return price;
    }
    private Coordinates makeCoor() {
        double x;
        float y;
        try {
            x = Double.parseDouble(coordinate_X.getText());
        } catch (IllegalArgumentException e) {
            makeredborder(coordinate_X);
            try {
                y = Float.parseFloat(coordinate_Y.getText());
            } catch (IllegalArgumentException b) {makeredborder(coordinate_Y);}
            throw e;
        }
        try {
            y = Float.parseFloat(coordinate_Y.getText());
        } catch (IllegalArgumentException e) {
            makeredborder(coordinate_Y);
            throw e;
        }
        return new Coordinates(x, y);
    }

    private Person makePerson() {
        if (personId.getText().isEmpty()) return null;
        String passId;
        long weight;
        passId = personId.getText();
        try {
            weight = Long.parseLong(this.weight.getText());
        } catch (IllegalArgumentException e) {
            makeredborder(this.weight);
            throw e;
        }
        return new Person(weight, passId);
    }

    private TicketType makeType() {
        if (comboBox.getValue() == null) return null;
        return TicketType.valueOf(comboBox.getValue().toString().toUpperCase());
    }

    public Ticket getTicket() {
        return ticket;
    }

    public static void setText() {
        title_label.setText(I18N.textForKey("text.inputtitle"));
        weight_label.setText(I18N.textForKey("text.inputweight"));
        price_label.setText(I18N.textForKey("text.inputprice"));
        X_label.setText(I18N.textForKey("text.inputX"));
        Y_label.setText(I18N.textForKey("text.inputY"));
        personId_label.setText(I18N.textForKey("text.inputid"));
        type_label.setText(I18N.textForKey("text.inputtype"));

    }

    private void makeredborder(TextField field) {
        field.setStyle("-fx-border-color: red;");
    }
}
