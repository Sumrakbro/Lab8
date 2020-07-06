package Lab5.com.company;

import Client.Client;
import Lab5.commands.A_command;
import Lab5.commands.Command_UpdateTable;
import Server.Command_Authorization;
import Server.User;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;

public class LoginWindow extends Application {
    private final String style2 = "S.css";
    private double xOffset;
    private double yOffset;
    private GridPane grid = new GridPane();
    private CheckBox serverIp=new CheckBox();
    private TextField ip = new TextField("Write other Ip(def:127.0.0.1)");
    private Scene scene = new Scene(grid, 250, 170);
    private Button reg_button = new Button("Registry");
    private Button signIn_button = new Button("Sign in");
    private Label login_label = new Label("Login:");
    private TextField login = new TextField();
    private Label password_label = new Label("Password:");
    private PasswordField password = new PasswordField();
    private ColumnConstraints columnConstraints = new ColumnConstraints();
    private ColumnConstraints columnConstraints1 = new ColumnConstraints();

    public LoginWindow() {
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        ip.setVisible(false);
        addElement();
        setSettings();
        reg_button.setOnAction(event -> {
            try {
                new RegistrationWindow().start(stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        signIn_button.setOnAction(event -> {
            try {
                if(serverIp.isSelected()&&!ip.getText().isEmpty()){
                    InetAddress serAdr;
                    try {
                        serAdr=InetAddress.getByName(ip.getText());
                        Client.setServerIP(serAdr);
                        signIn(stage);
                    } catch (UnknownHostException e){
                        ip.setStyle("-fx-border-color: #ff0000;-fx-border-radius: 10;");
                    }
                }
                else {
                    signIn(stage);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        serverIp.setOnMouseClicked(mouseEvent -> {
           if(!ip.isVisible()) ip.setVisible(true);
            else ip.setVisible(false);
        });

        scene.setOnMousePressed(event -> {
            xOffset = stage.getX() - event.getScreenX();
            yOffset = stage.getY() - event.getScreenY();
        });
        scene.setOnMouseDragged(event -> {

            stage.setX(event.getScreenX() + xOffset);
            stage.setY(event.getScreenY() + yOffset);
        });
        ip.setOnMouseClicked(mouseEvent -> {
            ip.setStyle("-fx-background-color:   #0896A0;\n" +
                    "\t-fx-background-radius:  10;\n" +
                    "\t-fx-text-fill: #FFFFFF;");
        });
        password.setOnMouseClicked(mouseEvent -> {
            password.setStyle("-fx-background-color:   #0896A0;\n" +
                    "\t-fx-background-radius:  10;\n" +
                    "\t-fx-text-fill: #FFFFFF;");
        });
        login.setOnMouseClicked(mouseEvent -> {
            login.setStyle("-fx-background-color:   #0896A0;\n" +
                    "\t-fx-background-radius:  10;\n" +
                    "\t-fx-text-fill: #FFFFFF;");
        });
        scene.getStylesheets().add(style2);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("");
        stage.show();
    }
    private void signIn(Stage stage) throws SQLException {
        String nick = login.getText();
        String pass = password.getText();
        System.out.println(nick + " " + pass);
        Command_Authorization command = new Command_Authorization(new User(nick, pass));
        Client.SendData(command);
        String answ=Client.ReceiveData().get(0);
        if (answ.equalsIgnoreCase("Successful")) {
            stage.close();
            Client.SendData(new Command_UpdateTable());
            Client.ReceiveData();
            System.out.println(A_command.getSet().first());
            new PersonalArea(nick).start(new Stage());
        }else{
            if(answ.equalsIgnoreCase("There is no user with this nickname")){
                login.setStyle("-fx-border-color: red;-fx-border-radius: 10;");
            }
            if(answ.equalsIgnoreCase("This login is already authorized")){
                new WarningForm().start(new Stage());
            } else  password.setStyle("-fx-border-color: red;-fx-border-radius: 10;");
        }
    }
    private void addElement() {
        grid.getColumnConstraints().addAll(columnConstraints, columnConstraints1);
        grid.add(login_label, 0, 0);
        grid.add(password_label, 0, 1);
        grid.add(login, 1, 0);
        grid.add(password, 1, 1);
        grid.add(reg_button, 0, 3);
        grid.add(signIn_button, 1, 3);
        grid.add(serverIp, 0, 2);
        grid.add(ip, 1,2);
    }

    private void setSettings() {
        columnConstraints.setPrefWidth(95);
        columnConstraints1.setPrefWidth(150);
        grid.setMargin(login, new Insets(10, 10, 0, 0));
        grid.setMargin(login_label, new Insets(10, 0, 0, 10));
        grid.setMargin(password_label, new Insets(10, 0, 0, 10));
        grid.setMargin(password, new Insets(10, 10, 0, 0));
        grid.setMargin(reg_button, new Insets(10, 10, 0, 20));
        grid.setMargin(signIn_button, new Insets(10, 20, 0, 70));
        grid.setMargin(serverIp, new Insets(10, 0, 0, 10));
        grid.setMargin(ip, new Insets(10, 10, 0, 0));
        grid.setVgap(5);
        grid.setHgap(5);
    }

}
