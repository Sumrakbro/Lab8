package Lab5.commands;


import Lab5.com.company.Color;
import Lab5.com.company.Receiver;
import Lab5.com.company.UncorrectedScriptException;

import java.io.IOException;

import java.util.ArrayList;

/**
 * Данная команда выполняет скрипт
 */
public class Command_ExecuteFile extends A_command implements Command {
    public final static boolean status=true;
    private String path;
    private static ArrayList<String> path_history = new ArrayList<>();
    private static String description="ExecuteFile";
    public Command_ExecuteFile(String path) throws UncorrectedScriptException {
        this.path = path;
        ArrayList<String> history=Command_ExecuteFile.getPath_history();
        for (String string:history) {
            if(string.compareTo(path)==0) {
                System.out.println(Color.ANSI_RED + "Попытка зацикливания!" + Color.ANSI_RESET);
                throw new  UncorrectedScriptException(Color.ANSI_RED+"Попытка зацикливания!\n"+Color.ANSI_RESET);
            }
        }
        path_history.add(path);
    }

    public Command_ExecuteFile() {

    }

    @Override
    public void setParam(Object o) {
        this.path=(String)o;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public String getPath() {
        return path;
    }
    public static void delete(String string){
        path_history.remove(string);
    }
    @Override
    public void setlogin(String login) {
        this.login = login;
    }

    public static ArrayList<String> getPath_history() {
        return path_history;
    }

    @Override
    public  void execute() throws IOException, UncorrectedScriptException {
        Receiver.ReadCommandFromFile(this.path);
    }

    @Override
    public String toString() {
        return "Command ExecuteFile <Path> " + this.path;
    }

    @Override
    public boolean getStatus() {
        return status;
    }

    @Override
    public String showAbility() {
        return "Command ExecuteFile <Path>- выпоняет скрипт";
    }
}
