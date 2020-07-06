package Server;


import Lab5.com.company.Receiver;
import Lab5.commands.A_command;
import Lab5.commands.Command;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class Command_RegisterUser extends A_command implements Command {
    @Override
    public void setlogin(String login) {
        this.login = login;
    }
    public final  boolean status=false;
    User user;
    public Command_RegisterUser(User user){
    this.user=user;
    }
    public Command_RegisterUser(){

    }

    @Override
    public void setParam(Object o) {

    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String showAbility() {
        return "Command RegisterUser<User>- регистрирует нового пользователя в БД";
    }

    @Override
    public void execute(){
        try {
            Receiver.registerUser(this.user,Server.getDatabase());
        } catch (SQLException e) {
            e.printStackTrace();
           }
    }
    @Override
    public boolean getStatus() {
        return status;
    }
}
