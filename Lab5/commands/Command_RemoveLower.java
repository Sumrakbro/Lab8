package Lab5.commands;


import Lab5.com.company.CommandExecutionException;
import Lab5.com.company.Receiver;
import Lab5.com.company.Ticket;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Данная команда удаляет все объекты коллекции,которые меньше заданного
 */
public class Command_RemoveLower extends Command_RemoveObjects {
    int index;
    public final static boolean status = true;
    private static String description="RemoveLower";
    public Command_RemoveLower(Ticket ticket) {
        super(ticket);
        this.index = -1;
    }

    public Command_RemoveLower() {

    }

    @Override
    public void setParam(Object o) {
        this.ticket=(Ticket)o;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getLogin() {
        return super.getLogin();
    }
    @Override
    public void setlogin(String login) {
        this.login = login;
    }
    @Override
    public void setLogin(String login) {
        super.setLogin(login);
    }

    @Override
    public void execute() throws SQLException, CommandExecutionException {
       Receiver.remove(A_command.getSet(), this.ticket, this.index, login);
    }

    @Override
    public String toString() {
        return "Command RemoveLower <Element>: " + this.ticket;
    }

    @Override
    public boolean getStatus() {
        return status;
    }

    @Override
    public String showAbility() {
        return "Command RemoveLower <Element>- удаляет все эллменты, которые меньше заданного";
    }
}
