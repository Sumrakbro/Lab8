package Lab5.com.company;

import Client.Client;
import Lab5.commands.A_command;
import Lab5.commands.Command;
import Lab5.commands.Command_UpdateTable;
import javafx.scene.control.*;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;


import java.lang.reflect.InvocationTargetException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CommandList extends ListView {

    MultipleSelectionModel<String> langsSelectionModel;
    TextField textField;
    Button sendCommand;
    private static Command command;

    public CommandList(TextField textField, Button sendCommand) {
        super(FXCollections.observableArrayList(A_command.getCommands()));
        this.textField = textField;
        this.sendCommand = sendCommand;
        settings();
    }

    public static void setParam(Object o) {
        System.out.println("Param " + o);
        if (o != null) command.setParam(o);
        command.setlogin(PersonalArea.getLogin());
        Client.SendData(command);
        PersonalArea.getTextArea().setText(PersonalArea.getTextArea().getText() + "\n" + Client.ReceiveData());
        Client.SendData(new Command_UpdateTable());
        Client.ReceiveData();
    }

    private void settings() {
        langsSelectionModel = this.getSelectionModel();
        langsSelectionModel.setSelectionMode(SelectionMode.MULTIPLE);
        langsSelectionModel.selectedItemProperty().addListener((changed, oldValue, newValue) -> {
            String selectedItems = "";
            ObservableList<String> selected = langsSelectionModel.getSelectedItems();
            for (String item : selected) {
                selectedItems += item + " ";
            }
            textField.setText("Selected: " + selectedItems);
        });
        this.setOnMouseClicked(mouseEvent -> {
            this.setStyle("-fx-border-color: transparent");
        });
        sendCommand.setOnAction(event -> {
            Class mClassObject;
            String selectedItems = "";
            ObservableList<String> selected = langsSelectionModel.getSelectedItems();
            for (String item : selected) {
                selectedItems += item + " ";
            }
            try {
                mClassObject = Class.forName("Lab5.commands.Command_" + new Scanner(selectedItems).nextLine().trim());
                command = Readder.ReadCommandFromConsole(mClassObject);
            } catch (ClassNotFoundException e) {
                try {
                    mClassObject = Class.forName("Server.Command_" + new Scanner(selectedItems).nextLine().trim());
                    command = Readder.ReadCommandFromConsole(mClassObject);
                } catch (ClassNotFoundException ex) {
                    System.out.println(Color.ANSI_RED + "Вы ввели несуществующую команду!" + Color.ANSI_RESET);
                } catch (IllegalAccessException | InstantiationException | InvocationTargetException ex) {
                    ex.printStackTrace();
                }

            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchElementException e) {
                this.setStyle("-fx-border-color: red");
            }
        });
    }

}
