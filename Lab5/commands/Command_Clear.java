package Lab5.commands;



import Lab5.com.company.PersonalArea;
import Lab5.com.company.Receiver;

import java.sql.SQLException;
import java.util.ArrayList;

/**
Данная команда очищает коллекцию
 */
public class Command_Clear extends A_command implements Command {
    private String login= PersonalArea.getLogin();
    public final static boolean status=true;
    private static String description="Clear";
    public Command_Clear() {
    }

    @Override
    public void setParam(Object o) {

    }

    @Override
    public String getDescription() {
        return description;
    }
    @Override
    public void setlogin(String login) {
        this.login = login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    @Override
    public boolean getStatus() {
        return  status;
    }
    @Override
    public void execute() throws SQLException {
        Receiver.clear(A_command.getSet(),this.login);
    }
    public String getLogin() {
        return login;
    }
    @Override
    public String toString() {
        return "Command Clear";
    }

    @Override
    public String showAbility() {
        return "Command Clear- очищает коллекцию";
    }
}

