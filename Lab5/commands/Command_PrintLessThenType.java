package Lab5.commands;

import Lab5.com.company.Receiver;
import Lab5.com.company.Ticket;
import Lab5.com.company.TicketType;

import java.util.ArrayList;

/**
 Данная команда выводит все объекты коллекции, тип которых меньше заданного
 */
public class Command_PrintLessThenType extends Command_Print {
    TicketType type;
    public final static boolean status=false;
    private static String description="PrintLessThenType";
    public Command_PrintLessThenType(TicketType type) {
    this.type=type;
    }

    @Override
    public void setParam(Object o) {
        this.type=(TicketType) o;
    }
    public Command_PrintLessThenType() {

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
    public void execute() {
         Receiver.print(A_command.getSet(),this.type);
    }
    @Override
    public String toString() {
        return "Command PrintLessThenType <Type>: "+ this.type;
    }
    @Override
    public void setlogin(String login) {
        this.login = login;
    }
    @Override
    public String showAbility() {
        return "Command PrintLessThenType <Type>- выводит элементы, значения Type которых меньше";
    }
}
