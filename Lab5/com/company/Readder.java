package Lab5.com.company;


import Lab5.commands.*;

import javafx.application.Platform;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class Readder {
    String path;

    public Readder(String path) {
        this.path = path;
    }

    public static Command ReadCommandFromConsole(Class claz) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Command command = null;
        Class mClassObject = claz;

        Constructor[] constructors = mClassObject.getConstructors();
        sorted(constructors);
        Class[] parameterTypes = constructors[0].getParameterTypes();
        if (parameterTypes.length == 0) {
            System.out.println("noun");
          //  new helpForm();
            command = (Command) constructors[0].newInstance();
        } else if (parameterTypes.length == 1) {
            if (parameterTypes[0].toString().compareTo("long") == 0) {
                formForReceivingId form = new formForReceivingId();
                form.start(new Stage());
                command = (Command) constructors[0].newInstance(-1);
            } else if (parameterTypes[0].toString().compareTo("class Lab5.com.company.Ticket") == 0) {
                System.out.println("Ticket");
                formForReceivingTicket form = new formForReceivingTicket();
                form.start(new Stage());
                command = (Command) constructors[0].newInstance(form.getTicket());
            } else if (parameterTypes[0].toString().compareTo("class Lab5.com.company.TicketType") == 0) {
                formForReceivingType form = new formForReceivingType();
                Stage stage=new Stage();
                form.start(stage);
                command = (Command) constructors[0].newInstance(form.getType());
            } else if (parameterTypes[0].toString().compareTo("class Lab5.com.company.Person") == 0) {
                formForReceivingPerson form=new formForReceivingPerson();
                form.start(new Stage());
                Person person = form.getPerson();
                command = (Command) constructors[0].newInstance(person);
            } else if (parameterTypes[0].toString().compareTo("class java.lang.String") == 0) {
                formForReceivingString form = new formForReceivingString();
                form.start(new Stage());
                command = (Command) constructors[0].newInstance(form.getString());
            }
        }


        System.out.println(command);
        return command;
    }

    private static void sorted(Constructor[] constructors) {
        boolean isSorted = false;
        Constructor buf;
        while (!isSorted) {
            isSorted = true;
            for (int i = 0; i < constructors.length - 1; i++) {
                if (constructors[i].getParameters().length < constructors[i + 1].getParameters().length) {
                    isSorted = false;
                    buf = constructors[i];
                    constructors[i] = constructors[i + 1];
                    constructors[i + 1] = buf;
                }
            }
        }
    }

    public static void AddCommand() {
        A_command.addNewCommand(new Command_RemoveLower(),
                new Command_Add(), new Command_ExecuteFile(),
                new Command_PrintLessThenType(), new Command_PrintUniquePerson(), new Command_RemoveByIndex(),
                new Command_RemoveGreater());
    }
}


