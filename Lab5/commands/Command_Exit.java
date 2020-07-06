package Lab5.commands;

import Lab5.com.company.Receiver;


import java.util.ArrayList;
import java.util.Collections;

/**
 Данная команда осуществялет выход из программы
 */
public class Command_Exit extends A_command implements Command {
    public final static boolean status=false;
    public Command_Exit() {

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
    @Override
    public boolean getStatus() {
        return  status;
    }
    @Override
    public void execute()
    {
        Receiver.exit();
    }

    @Override
    public String toString() {
        return "Command Exit";
    }

    @Override
    public String showAbility() {
        return "Command Exit- выходит из программы без сохранения изменений";
    }
}
