package Lab5.commands;


import Lab5.com.company.CommandExecutionException;
import Lab5.com.company.Receiver;
import Lab5.com.company.UncorrectedScriptException;

import java.io.IOException;
import java.sql.SQLException;

public class Command_UpdateTable extends A_command implements Command{

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
    public String showAbility() {
        return null;
    }

    @Override
    public void execute() throws IOException, UncorrectedScriptException, CommandExecutionException, CommandExecutionException, UncorrectedScriptException, SQLException {
        Receiver.updateTable();
    }

    @Override
    public boolean getStatus() {
        return false;
    }
}
