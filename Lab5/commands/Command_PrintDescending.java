package Lab5.commands;



import Lab5.com.company.Receiver;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 Данная команда выводит коллекцию в обратном порядке
 */
public class Command_PrintDescending extends Command_Print{
int i=-1;
    public final static boolean status=false;
    private static String description="PrintDescending";
    public Command_PrintDescending() {
    }

    @Override
    public void setParam(Object o) {

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
    public void execute() throws SQLException {
        Receiver.print(A_command.getSet(),this.i);
        }
    @Override
    public void setlogin(String login) {
        this.login = login;
    }
    @Override
    public String toString() {
        return "Command PrintDescending";
    }

    @Override
    public String showAbility() {

            return "Command PrintDescending-выводит коллекцию в обратном порядке";
    }
}
