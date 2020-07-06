package Lab5.commands;

import Lab5.com.company.Ticket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;
import java.util.stream.Stream;

public abstract class A_command implements Command {
    public A_command() {

    }

    protected String login;


    private static TreeSet<Ticket> set;

    public abstract String getDescription();

    public static void setSet(TreeSet<Ticket> set) {
        A_command.set = set;
    }

    public static ArrayList<String> getAllOwners() {
        ArrayList<String> owners = new ArrayList<>();
        for (Ticket t : set) {
            if (!owners.contains(t.getOwner()) && t.getOwner() != null && !t.getOwner().isEmpty())
                owners.add(t.getOwner());
        }
        return owners;
    }



    public static ArrayList<Ticket> getAllUsersTicket(String login) {
        ArrayList<Ticket> tickets = new ArrayList<>();
        int k = 0;
        for (Ticket i : set) {
            if (i.getOwner() != null && !i.getOwner().isEmpty()) {
                if (i.getOwner().equals(login)) {
                    tickets.add(i);
                }
            }
            k++;
        }
        return tickets;
    }


  public static Ticket getTicketById(long id){
      for (Ticket t:set) {
          if (t.getId()==id) return t;
      }
    return null;
  }

    public static TreeSet<Ticket> getSet() {
        return set;
    }

    private static ArrayList<String> commands = new ArrayList<>();

    public static ArrayList<String> getCommands() {
        return commands;
    }

    @Override
    public abstract boolean getStatus();

    public static void addNewCommand(A_command... commandsList) {
        for (A_command command : commandsList) {
            commands.add(command.getDescription());
        }
    }

}
