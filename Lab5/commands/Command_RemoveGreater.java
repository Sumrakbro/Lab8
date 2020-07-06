package Lab5.commands;

import Lab5.com.company.CommandExecutionException;
import Lab5.com.company.Receiver;
import Lab5.com.company.Ticket;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 Данная команда удаляет все объекты коллекции,  которые больше заданного
 */
public class Command_RemoveGreater extends  Command_RemoveObjects{
    int index;
    public final static boolean status=true;
    private static String description="RemoveGreater";
    public Command_RemoveGreater(Ticket ticket) {
        super(ticket);
        this.index=1;

    }
    @Override
    public void setParam(Object o) {
        this.ticket=(Ticket)o;
    }
    public Command_RemoveGreater() {
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean getStatus() {
        return  status;
    }

    @Override
    public void setLogin(String login) {
        super.setLogin(login);
    }

    @Override
    public String getLogin() {
        return super.getLogin();
    }

    @Override
    public void execute() throws SQLException, CommandExecutionException {
        Receiver.remove(A_command.getSet(),this.ticket,this.index,login);
    }
    @Override
    public String toString() {
        return "Command RemoveGreater <Element>: "+this.ticket;
    }
    @Override
    public void setlogin(String login) {
        this.login = login;
    }
    @Override
    public String showAbility() {
        return "Command RemoveGreater <Element>- удаляет все эллменты, которые больше заданного";
    }
}
