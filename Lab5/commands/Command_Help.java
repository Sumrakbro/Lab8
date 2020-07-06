package Lab5.commands;

import Lab5.com.company.Receiver;


import java.util.ArrayList;

/**
 Даннавя комманда показывает все доступные комманды.
 */

public class Command_Help extends A_command implements Command{
    public final static boolean status=false;
    public Command_Help(){

   }

    @Override
    public void setParam(Object o) {

    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public boolean getStatus() {
        return  status;
    }
    @Override
    public void execute() {
       //return Receiver.printHelp();
    }
    @Override
    public void setlogin(String login) {
        this.login = login;
    }
    @Override
    public String toString() {
        return "Command Help";
    }

    @Override
    public String showAbility() {

            return "Command Help- показывает доступные комманды";
    }
}
