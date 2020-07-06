package Lab5.commands;

import Lab5.com.company.CommandExecutionException;
import Lab5.com.company.PersonalArea;
import Lab5.com.company.Receiver;
import Lab5.com.company.Ticket;

import java.sql.SQLException;
import java.util.ArrayList;

public class Command_Update extends A_command implements Command {
    public final static boolean status=true;
  String login;
    Long index;
    Ticket ticket;
    /**
     Данная команда заменяет объект коллекции по id
     */
    public Command_Update(long index, Ticket ticket) {
        this.login= PersonalArea.getLogin();
        this.index = index;
        this.ticket = ticket;
    }

    public Command_Update() {

    }

    @Override
    public void setParam(Object o) {

    }

    @Override
    public String getDescription() {
        return null;
    }
    @Override
    public void setlogin(String login) {
        this.login = login;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public void execute() throws CommandExecutionException, SQLException {
       Receiver.replace(this.index, this.ticket, A_command.getSet(),login);
    }
    @Override
    public String toString() {
        return "Command Update <Index,Element>: "+this.index+" "+this.ticket;
    }

    @Override
    public boolean getStatus() {
        return  status;
    }

    @Override
    public String showAbility() {
        return "Command Update <Index,Element>- обновляет эллемент id,которого равно заданному";
    }
}
