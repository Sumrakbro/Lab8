package Server;

import Lab5.com.company.CommandExecutionException;
import Lab5.com.company.PersonalArea;
import Lab5.com.company.Receiver;
import Lab5.com.company.UncorrectedScriptException;
import Lab5.commands.A_command;
import Lab5.commands.Command;

import java.io.IOException;
import java.util.ArrayList;

public class Command_Disconnect extends A_command {
    public final boolean status = true;
    public static String username;

    public Command_Disconnect() {

    }

    public Command_Disconnect(String login) {
        this.login = login;
    }

    @Override
    public void setParam(Object o) {

    }

    @Override
    public void setlogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String showAbility() {
        return "Command Disconnect<>- выполняет выход из учетной записи в БД";
    }

    @Override
    public void execute() {
        Receiver.Disconnect(login);
    }

    @Override
    public boolean getStatus() {
        return status;
    }
}
