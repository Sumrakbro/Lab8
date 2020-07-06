package Lab5.commands;

import Lab5.com.company.Receiver;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 Данная команда выводит все объекты коллекции
 */
public class Command_Show extends A_command implements Command{
    public final boolean status=false;
    private static String description="Show";
    public Command_Show(){
   }

    @Override
    public void setParam(Object o) {

    }
    @Override
    public void setlogin(String login) {
        this.login = login;
    }
    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void execute() throws SQLException {
        Receiver.print(A_command.getSet(),1);
   }
    @Override
    public String toString() {
        return "Command Show";
    }

    @Override
    public String showAbility() {
        return "Command Show- выводит эллементы коллекции на экран";
    }
    @Override
    public boolean getStatus() {
        return  status;
    }
}
