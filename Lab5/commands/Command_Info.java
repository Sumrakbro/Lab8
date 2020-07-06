package Lab5.commands;


import Lab5.com.company.Receiver;

import java.util.ArrayList;

/**
 * Команда показывающая информацию о коллекции
 */
public class Command_Info extends A_command implements Command {
    public final static boolean status = false;
    private static String description="Info";
    public Command_Info() {
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
    public boolean getStatus() {
        return status;
    }

    @Override
    public void execute() {
         Receiver.info(A_command.getSet());
    }

    @Override
    public String toString() {
        return "Command Info";
    }

    @Override
    public String showAbility() {
        return "Command Info- выводит информацию о коллекции";
    }
}
