package Lab5.com.company;;


import Client.Client;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

/**
 Данная программа- пример управления коллекцией.
 Выполнена по паттерну: Команда
 Тип коллекции:TreeSet
 @author Balakin Artem
 @since Java8
 */
public class Main {

    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException, InvocationTargetException, ClassNotFoundException, SQLException {
        new Client();
        Readder.AddCommand();
        LoginWindow.main(args);
    }
}

