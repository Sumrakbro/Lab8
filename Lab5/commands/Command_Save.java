package Lab5.commands;

import Lab5.com.company.CommandExecutionException;
import Lab5.com.company.Receiver;

import java.io.IOException;
import java.util.ArrayList;

/**
 Данная команда сохраняет коллекцию в файл
 */
public class Command_Save extends A_command implements Command{
   String path;
    public final static boolean status=true;
    public Command_Save(String path){
        this.path=path;
    }

    public Command_Save() {
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
        return null;
    }

    @Override
    public void execute() throws IOException, CommandExecutionException {
   Receiver.save(A_command.getSet(),this.path);
    }

    public String getPath() {
        return path;
    }
    @Override
    public String toString() {
        return "Command Save <Path>: "+this.path;
    }

    @Override
    public String showAbility() {
        return "Command Save <Path>- сохраняет коллекцию в файл";
    }
    @Override
    public boolean getStatus() {
        return  status;
    }
}


