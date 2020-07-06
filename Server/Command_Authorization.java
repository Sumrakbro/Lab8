package Server;


import Lab5.com.company.Receiver;
import Lab5.commands.A_command;
import Lab5.commands.Command;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class Command_Authorization extends A_command implements Command {
    public final boolean status = false;
    User user;

    public Command_Authorization(User user) {
        this.user = user;
    }
    public Command_Authorization() {

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
        return status;
    }
    @Override
    public void setlogin(String login) {
        this.login = login;
    }
    @Override
    public String showAbility() {
        return "Command Authorization<User>- делает попытку входа с данным логином и паролем";
    }

    @Override
    public void execute() {
        try {
          Receiver.AuthorizationUser(this.user, Server.getDatabase());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
