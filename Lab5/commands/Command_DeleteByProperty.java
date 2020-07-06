package Lab5.commands;
import Lab5.com.company.CommandExecutionException;
import Lab5.com.company.Receiver;
import Lab5.com.company.Ticket;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public abstract class Command_DeleteByProperty extends A_command implements Command {


    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public Command_DeleteByProperty(){
    }
    @Override
    public void execute() throws CommandExecutionException, SQLException {
    }
    @Override
    public String toString() {
        return "Command Remove";
    }
}
