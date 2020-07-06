package Lab5.commands;
import Lab5.com.company.CommandExecutionException;
import Lab5.com.company.Receiver;
import Lab5.com.company.Ticket;

import java.util.ArrayList;

/**
 Данная команда удаляет  объекты из коллекции по индексу
 */
public class Command_RemoveByIndex extends  Command_DeleteByProperty{
    Long index;
    public final static boolean status=true;
    public Command_RemoveByIndex( long index){
        this.index=index;
    }
    private static String description="RemoveByIndex";
    public Command_RemoveByIndex() {
    }

    @Override
    public String getDescription() {
        return description;
    }


    @Override
    public String getLogin() {
        return super.getLogin();
    }

    @Override
    public void setParam(Object o) {
        this.index=(Long) o;
    }

    @Override
    public void execute() throws CommandExecutionException {
         Receiver.remove(A_command.getSet(),this.index,this.getLogin());
    }
    @Override
    public boolean getStatus() {
        return  status;
    }
    @Override
    public String toString() {
        return "CommandRemoveByIndex <Index>:"+ this.index;
    }

    @Override
    public String showAbility() {
       return  "CommandRemoveByIndex <Index>- удаляет эллемент с таким индексом";
    }

    @Override
    public void setlogin(String login) {

    }
}
