package Lab5.commands;

import Lab5.com.company.CommandExecutionException;
import Lab5.com.company.Receiver;
import Lab5.com.company.Ticket;
import Lab5.com.company.UncorrectedScriptException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.TreeSet;

public class Command_UpdateCollection extends A_command{
    TreeSet<Ticket> newCollection;
    public Command_UpdateCollection(){
        newCollection=A_command.getSet();
    }
    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String showAbility() {
        return null;
    }

    @Override
    public void setlogin(String login) {
        this.login = login;
    }

    @Override
    public void execute() throws CommandExecutionException, SQLException {
        Receiver.updateCollection(this.newCollection);
    }

    @Override
    public void setParam(Object o) {

    }

    @Override
    public boolean getStatus() {
        return false;
    }
}
