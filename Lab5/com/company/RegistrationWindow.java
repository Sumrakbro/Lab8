package Lab5.com.company;

import Client.Client;
import Server.Command_RegisterUser;
import Server.User;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class RegistrationWindow extends Application {
    private TextField name = new TextField();
    private TextField surname = new TextField();
    private TextField nickname = new TextField();
    private TextField mail = new TextField();
    private Label name_label = new Label("    Name:");
    private Label surname_label = new Label(" Surname:");
    private Label nick_label = new Label("    Nick:");
    private Label pass_label = new Label("Password:");
    private Label rpass_label = new Label("Password:");
    private Label mail_label = new Label("    Mail:");
    private PasswordField new_password = new PasswordField();
    private PasswordField repeat_newPass = new PasswordField();
    private GridPane pane = new GridPane();
    private Stage reg_window = new Stage();
    private Button registry = new Button("Registry");
    private String style2 = "S.css";

    public RegistrationWindow() {
        addEllement();
        window_settings();
    }

    @Override
    public void start(Stage stage) {

        registry.setOnAction(event -> {
            Command_RegisterUser command_registerUser = new Command_RegisterUser(makeUser());
            Client.SendData(command_registerUser);
            Client.ReceiveData();
        });
        Scene reg_scene = new Scene(pane, 250, 350);
        reg_window.setScene(reg_scene);
        reg_window.initOwner(stage);
        reg_window.setX(stage.getX() + 200);
        reg_window.setY(stage.getY() + 100);
        reg_scene.getStylesheets().add(style2);
        reg_window.show();
    }

    private void addEllement() {
        pane.add(name_label, 0, 1);
        pane.add(name, 1, 1);
        pane.add(surname_label, 0, 2, 2, 1);
        pane.add(surname, 1, 2);
        pane.add(nick_label, 0, 3);
        pane.add(nickname, 1, 3);
        pane.add(mail_label, 0, 4);
        pane.add(mail, 1, 4);
        pane.add(pass_label, 0, 5, 2, 1);
        pane.add(new_password, 1, 5);
        pane.add(rpass_label, 0, 6, 2, 1);
        pane.add(repeat_newPass, 1, 6);
        pane.add(registry, 1, 7, 3, 1);
    }

    private void window_settings() {
        registry.setMinWidth(130);
        pane.setHgap(10);
        pane.setVgap(21);
        pane.setMargin(name_label, new Insets(10, 0, 0, 10));
        pane.setMargin(surname_label, new Insets(0, 0, 0, 10));
        pane.setMargin(nick_label, new Insets(0, 0, 0, 10));
        pane.setMargin(mail_label, new Insets(0, 0, 0, 10));
        pane.setMargin(pass_label, new Insets(0, 0, 0, 10));
        pane.setMargin(rpass_label, new Insets(0, 0, 10, 10));
        reg_window.initStyle(StageStyle.UTILITY);

        reg_window.initModality(Modality.WINDOW_MODAL);
    }

    private User makeUser() {
        String name = null;
        String surname = null;
        String nick = null;
        String mail = null;
        String password = null;

        if (!this.name.getText().isEmpty()) name = this.name.getText();
        if (!this.surname.getText().isEmpty()) surname = this.surname.getText();
        if (!this.nickname.getText().isEmpty()) nick = this.nickname.getText();
        if (!this.mail.getText().isEmpty()) mail = this.mail.getText();
        if (!this.new_password.getText().isEmpty()) password = this.new_password.getText();
        if (name != null && surname != null && nick != null && password != null && mail != null) {
            if (!name.isEmpty() && !surname.isEmpty() && !nick.isEmpty() && !password.isEmpty() && !mail.isEmpty()) {
                System.out.println(name+" "+surname+" "+nick+" "+mail+" "+password);
                User user = new User(name, surname, nick, mail,password);
                System.out.println(user);
                return user;
            } else {
                System.out.println("дурак");
                return null;
            }
        } else {
            System.out.println("дурак");
            return null;
        }
    }
}
