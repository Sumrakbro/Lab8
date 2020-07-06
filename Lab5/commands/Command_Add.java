package Lab5.commands;

import Lab5.com.company.Receiver;
import Lab5.com.company.Ticket;


import java.sql.SQLException;
import java.util.ArrayList;

/**
 Данная команда добавляет объект в коллекцию
 */

public class Command_Add extends A_command implements Command {
    public final static boolean status=true;
   private static String description="Add";
    Ticket ticket;
    String login;
    public Command_Add(Ticket ticket) {
        this.ticket = ticket;
    }
    public Command_Add() {
    }

    @Override
    public String getDescription() {
        return description;
    }
    @Override
    public void setlogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }



    @Override
    public boolean getStatus() {
        return  status;
    }
    @Override
    public void execute() throws SQLException {
         Receiver.add(this.ticket,login);
    }

    @Override
    public void setParam(Object o) {
        this.ticket=(Ticket)o;
    }

    @Override
    public String toString() {
        return "Command Add <Ticket>: "+ this.ticket;
    }

    @Override
    public String showAbility() {
        return "Command Add <Ticket>-добавляет эллемент в коллекцию";
    }
}
