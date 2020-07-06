package Lab5.com.company;


import Lab5.commands.A_command;
import Lab5.commands.Command;
import Lab5.commands.Command_ExecuteFile;
import Server.Database;
import Server.Server;
import Server.User;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class Receiver {
    private static ArrayList<String> answers = new ArrayList<>();

    public static void updateCollection(TreeSet<Ticket> newSet) throws CommandExecutionException, SQLException {
        clearall();
        for (Ticket t : newSet) {
            if (t.getOwner() != null) replace(t.getId(), t, A_command.getSet(), t.getOwner());
        }
        Database.readData();
        answers = new ArrayList<>(Collections.singleton("Update"));
    }

    public static ArrayList getAnswers() {
        ArrayList<String> result = new ArrayList<>(answers);
        answers.clear();
        return result;
    }

    public static void registerUser(User user, Database database) throws SQLException {
        answers = database.registerUser(user);
    }

    public static void AuthorizationUser(User user, Database database) throws SQLException {
        answers = database.AuthorizationUser(user);
    }

    public static void Disconnect(String login) {
        answers = new ArrayList<>(Collections.singleton("Disconnect. Bye=) "));
        System.out.println(login);
        Server.deleteUser(login);
    }

    public synchronized static void replace(long index, Ticket ticket, TreeSet<Ticket> collection, String login) throws CommandExecutionException, SQLException {
        if (ticket.getOwner() != null && !ticket.getOwner().isEmpty()) {
            remove(collection, index, login);
            String command = "INSERT INTO Tickets VALUES (" + index + "," + TicketToSQLCommand(ticket) + ",'" + login + "'" + ");";
            Database.executor(command);
            Database.readData();
            answers = new ArrayList<>(Collections.singleton("Эллемент обновлен"));
        } else answers = new ArrayList<>(Collections.singleton("Неправильные данные"));
    }

    public synchronized static void updateTable() throws SQLException {
        System.out.println("Update");
        Database.readData();
        answers = new ArrayList<>(Collections.singleton("Update"));

    }

    public synchronized static void clear(TreeSet<Ticket> collection, String login) throws SQLException {
        Database.executor("DELETE FROM Tickets WHERE owner ='" + login + "';");
        Database.readData();
        answers = new ArrayList<>(Collections.singleton("Ваша коллекция очищена " + login));

    }

    public synchronized static void remove(TreeSet<Ticket> collection, long index, String login) throws CommandExecutionException {
        System.out.println(login);
        ArrayList<String> result = new ArrayList<>();
        boolean k = false;
        for (Ticket t : collection) {
            System.out.println(t);
            if (t.getId() == index && t.getOwner().equals(login)) k = true;
        }
        if (!k) {

            throw new CommandExecutionException("В коллекции нет элемента. Или вам не принадлежит.");
        }
        collection.removeIf(w -> w.getId() == index);
        Database.executor("DELETE FROM Tickets WHERE id ='" + index + "';");
        if (collection.size() == 0) {
            result.add("Коллекция пуста.");
            answers = result;
            return;
        }
        for (Ticket tik : collection) {
            result.add(tik.toString());
        }
        for (Object s : result) {
            System.out.println(s);
        }
        answers = result;

    }


    public synchronized static void remove(TreeSet<Ticket> collection, Ticket ticket, int i, String login) throws SQLException, CommandExecutionException {
      TreeSet<Ticket> col=collection;
        if (i > 0) {
            collection.removeIf(t -> (t.compareTo(ticket) > 0) && (t.getOwner().equals(login)));
        } else {
            collection.removeIf(t -> (t.compareTo(ticket) < 0) && (t.getOwner().equals(login)));
        }
        ArrayList<String> result = new ArrayList<>();
        for (Ticket tik : collection) {
            result.add(tik.toString());
        }
        clearall();
        for (Ticket tik : col) {
            replace(tik.getId(),tik,collection,login);
        }
        if (result.isEmpty()) result.add("Ваша коллекция пуста " + login);
        answers = result;

    }

    public static void exit() {
        System.exit(0);
    }

    private synchronized static void clearall() {
        Database.executor("DELETE FROM Tickets WHERE id >'" + 0 + "';");
    }

    public synchronized static void add(Ticket ticket, String login) throws SQLException {
        if (login != null && !login.isEmpty()) {
            System.out.println("Туть");
            String sql = "INSERT INTO Tickets (ID,Title,coordinates,creationdate,price,type,person,owner)" +
                    " Values (nextval('iterator'), ?, ?, Now(), ?,'" + ticket.getType() + "', ?, ?)";
            PreparedStatement preparedStatement = Database.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, ticket.getName());
            if (ticket.getCoordinates() != null) preparedStatement.setString(2, ticket.getCoordinates().CoorToSQL());
            else preparedStatement.setString(2, null);
            preparedStatement.setDouble(3, ticket.getPrice());
            if (ticket.getPerson() != null) preparedStatement.setString(4, ticket.getPerson().PersonToSQL());
            else preparedStatement.setString(4, null);
            preparedStatement.setString(5, login);
            preparedStatement.execute();
            Database.readData();
            answers = new ArrayList<>(Collections.singleton("Эллемент добавлен"));
            System.out.println("Туть2");
        }
       else answers = new ArrayList<>(Collections.singleton(""));

    }

    private static String TicketToSQLCommand(Ticket ticket) {
        if (ticket.getType() != null && ticket.getPerson() != null)
            return "'" + ticket.getName() + "',(" + ticket.getCoordinates().getX() + "," + ticket.getCoordinates().getY() + ")," + "Now(),'" +
                    ticket.getPrice() + "','" + ticket.getType().toString().toLowerCase() + "',('" +
                    ticket.getPerson().getPassportID() + "'," + ticket.getPerson().getWeight() + ")";
        else if (ticket.getType() != null)
            return "'" + ticket.getName() + "',(" + ticket.getCoordinates().getX() + "," + ticket.getCoordinates().getY() + ")," + "Now(),'" +
                    ticket.getPrice() + "','" + ticket.getType().toString().toLowerCase() + "'," +
                    null;
        else if (ticket.getPerson() != null)
            return "'" + ticket.getName() + "',(" + ticket.getCoordinates().getX() + "," + ticket.getCoordinates().getY() + ")," + "Now(),'" +
                    ticket.getPrice() + "'," + null + ",('" +
                    ticket.getPerson().getPassportID() + "'," + ticket.getPerson().getWeight() + ")";
        else
            return "'" + ticket.getName() + "',(" + ticket.getCoordinates().getX() + "," + ticket.getCoordinates().getY() + ")," + "Now(),'" +
                    ticket.getPrice() + "'," + null + "," + null;
    }

    public synchronized static void save(TreeSet<Ticket> set, String path) throws IOException, CommandExecutionException {
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = new FileOutputStream(path);
        } catch (FileNotFoundException e) {
            System.out.println("Ошибка с доступом к файлу!");
            throw new CommandExecutionException("Ошибка с доступом к файлу!");
        }
        for (Ticket ticket : set) {
            fileOutputStream.write(ticket.toString().getBytes());
        }
        fileOutputStream.close();
        System.out.println(Color.ANSI_PURPLE + "Коллекция сохранена!" + Color.ANSI_RESET);
        answers = new ArrayList<>(Collections.singleton(Color.ANSI_PURPLE +
                "Коллекция сохранена!" + Color.ANSI_RESET));
    }

    public static void print(TreeSet<Ticket> set, int type) throws SQLException {
        ArrayList<String> result = new ArrayList<>();
        if (set.size() > 0) {
            if (type > 0) {
                for (Object ticket : set) {
                    result.add(ticket.toString());
                }
            } else {
                for (Object ticket : set.descendingSet()) {
                    System.out.println(ticket);
                    result.add(ticket.toString());
                }
            }
        } else {
            System.out.println(Color.ANSI_CYAN + "Коллекция пуста!" + Color.ANSI_RESET);
            result.add(Color.ANSI_CYAN + "Коллекция пуста!" + Color.ANSI_RESET);
        }
        answers = result;
    }


    public synchronized static void print(TreeSet<Ticket> set, Person person) {
        ArrayList<String> result = new ArrayList<>();
        for (Ticket ticket1 : set) {
            if (ticket1.getPerson() != null) {
                if (!ticket1.getPerson().equals(person)) {
                    System.out.println(ticket1.getPerson());
                    result.add(ticket1.getPerson().toString());
                }
            } else {
                String answer = Color.ANSI_YELLOW + "Билет с номером: " + ticket1.getId()
                        + " не содержит значения Person" + Color.ANSI_RESET;
                System.out.println(answer);
                result.add(answer);
            }
        }
        answers = result;
    }

    public synchronized static void print(TreeSet<Ticket> set, TicketType type) {
        ArrayList<String> result = new ArrayList<>();
        for (Ticket ticket : set) {
            if (ticket.getType() != null&&type!=null) {
                if (ticket.getType().compareTo(type) < 0) {
                    result.add(ticket.toString());
                }
            } else{
                if(ticket.getType() == null&&type!=null)  result.add(ticket.toString());
            }
        }
        answers = result;
        if(answers.isEmpty())answers.add("Таких эллементов нет");
    }

    public static void info(TreeSet<Ticket> set) {
        ArrayList<String> result = new ArrayList<>();
        result.add(Color.ANSI_CYAN + "Общая информация о коллекции:");
        result.add("\tРазмер коллекции:" + set.stream().count() + Color.ANSI_RESET);
        System.out.println(Color.ANSI_CYAN + "Общая информация о коллекции:");
        System.out.println("\tРазмер коллекции:" + set.size() + Color.ANSI_RESET);
        answers = result;
    }

    public synchronized static void ReadCommandFromFile(String path) throws UncorrectedScriptException, IOException {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(path));
        } catch (IOException e) {
            System.out.println(Color.ANSI_RED + "Ошибка в пути к файлу!" + Color.ANSI_RESET);
            throw new UncorrectedScriptException(Color.ANSI_RED + "Ошибка в пути к файлу!" + Color.ANSI_RESET);
        }
        // считываем построчно
        String line;
        Scanner scanner;
        String input;
        Class mClassObject;
        Command command;
        String name = null;
        while ((line = reader.readLine()) != null) {
            command = null;
            System.out.println("input: " + line);
            scanner = new Scanner(line);
            while (scanner.hasNext()) {
                while (true) {
                    try {
                        input = scanner.nextLine() + " ";
                        name = input.substring(0, input.indexOf(" "));
                        mClassObject = Class.forName("commands.Command_" + name);
                        break;
                    } catch (ClassNotFoundException e) {
                        System.out.println(Color.ANSI_RED + "Несуществующая команда  " + name + "!" + Color.ANSI_RESET);
                        throw new UncorrectedScriptException(Color.ANSI_RED + "Несуществующая команда  " + name + "!" + Color.ANSI_RESET);
                    }
                }
                input = input.trim();
                Constructor[] constructors = mClassObject.getConstructors();
                Class[] parameterTypes = constructors[0].getParameterTypes();
                try {
                    if (parameterTypes.length == 0) {
                        command = (Command) constructors[0].newInstance();
                        System.out.println("Полученная из скрипта команда:\n" + Color.ANSI_CYAN + command + Color.ANSI_RESET);
                    } else if (parameterTypes.length == 1) {
                        if (parameterTypes[0].toString().compareTo("int") == 0) {
                            command = (Command) constructors[0].newInstance(Integer.parseInt(input.substring(input.lastIndexOf(" ") + 1)));
                            System.out.println("Полученная из скрипта команда:\n" + Color.ANSI_CYAN + command + Color.ANSI_RESET);
                        } else if (parameterTypes[0].toString().compareTo("class com.company.Ticket") == 0) {
                            command = (Command) constructors[0].newInstance(MakeTicket(reader));
                            System.out.println("Полученная из скрипта команда:\n" + Color.ANSI_CYAN + command + Color.ANSI_RESET);
                        } else if (parameterTypes[0].toString().compareTo("class com.company.TicketType") == 0) {
                            command = (Command) constructors[0].newInstance(MakeType(reader));
                            System.out.println("Полученная из скрипта команда:\n" + Color.ANSI_CYAN + command + Color.ANSI_RESET);
                        } else if (parameterTypes[0].toString().compareTo("class com.company.Person") == 0) {
                            command = (Command) constructors[0].newInstance(MakePerson(reader));
                            System.out.println("Полученная из скрипта команда:\n" + Color.ANSI_CYAN + command + Color.ANSI_RESET);
                        } else if (parameterTypes[0].toString().compareTo("class java.lang.String") == 0) {
                            command = (Command) constructors[0].newInstance(input.substring(input.indexOf(" ") + 1));

                            System.out.println("Полученная из скрипта команда:\n" + Color.ANSI_CYAN + command + Color.ANSI_RESET);
                        }

                    } else if (parameterTypes.length == 2) {
                        command = (Command) constructors[0].newInstance(Integer.parseInt(input.substring(input.indexOf(" ") + 1)),
                                MakeTicket(reader));
                        System.out.println("Полученная из скрипта команда:\n" + Color.ANSI_CYAN + command + Color.ANSI_RESET);
                    }
                } catch (UncorrectedScriptException e) {
                    throw e;
                } catch (IOException | InvocationTargetException | InstantiationException | IllegalAccessException e) {

                    e.getCause();
                }
                if (command != null) {
                    try {
                        command.execute();
                    } catch (CommandExecutionException | SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        Command_ExecuteFile.delete(path);
        answers = new ArrayList<>(Collections.singleton("Скрипт выполнен успешно"));
    }

    private static Ticket MakeTicket(BufferedReader reader) throws IOException, UncorrectedScriptException {
        String name = null; //Поле не может быть null, Строка не может быть пустой
        Float price; //Поле может быть null, Значение поля должно быть больше 0
        TicketType type; //Поле может быть null
        Scanner scanner;
        String input = "";
        while (input.isEmpty()) {
            input = reader.readLine();
            scanner = new Scanner(input);
            if (!input.isEmpty()) name = scanner.next().trim();
        }
        System.out.println("Name:" + name);
        price = getFloat(reader);
        System.out.println("Price:" + price);
        type = MakeType(reader);
        return new Ticket(name, MakeCoordinates(reader), price, type, MakePerson(reader));
    }

    private static Float getFloat(BufferedReader reader) throws IOException, UncorrectedScriptException {
        Float result = null;
        Scanner scanner;
        String input = "";
        while (input.isEmpty()) {
            input = reader.readLine();
            scanner = new Scanner(input);
            try {
                if (!input.isEmpty()) result = scanner.nextFloat();
            } catch (InputMismatchException e) {
                String message = Color.ANSI_RED + "Неверные данные в скрипте: " + Color.ANSI_YELLOW
                        + input + Color.ANSI_RED + ". Проверьте Скрипт!\n"
                        + Color.ANSI_YELLOW + "Данная строка должна содержать дробное число." + Color.ANSI_RESET;
                System.out.println(message);
                throw new UncorrectedScriptException(message);
            }
        }
        return result;
    }

    private static Coordinates MakeCoordinates(BufferedReader reader) throws IOException, UncorrectedScriptException {
        Float x; //Значение поля должно быть больше -373, Поле не может быть null
        Integer y;
        x = getFloat(reader);
        System.out.println("X:" + x);
        y = getInteger(reader);
        System.out.println("Y:" + y);
        return new Coordinates(x, y);
    }

    private static Integer getInteger(BufferedReader reader) throws UncorrectedScriptException, IOException {
        String input = "";
        Scanner scanner;
        Integer result = null;
        while (input.isEmpty()) {
            input = reader.readLine();
            scanner = new Scanner(input);
            try {
                if (!input.isEmpty()) result = scanner.nextInt();
            } catch (InputMismatchException e) {
                String message = Color.ANSI_RED + "Неверные данные в скрипте: " +
                        Color.ANSI_YELLOW + input + Color.ANSI_RED + ". Проверьте Скрипт!\n"
                        + Color.ANSI_YELLOW + "Данная строка должна содержать целое число." + Color.ANSI_RESET;
                System.out.println(message);
                throw new UncorrectedScriptException(message);
            }
        }
        return result;
    }

    private static Long getLong(BufferedReader reader) throws IOException, UncorrectedScriptException {
        String input = "";
        Scanner scanner;
        Long result = null;
        while (input.isEmpty()) {
            input = reader.readLine();
            scanner = new Scanner(input);
            try {
                if (!input.isEmpty()) result = scanner.nextLong();
            } catch (InputMismatchException e) {
                String message = Color.ANSI_RED + "Неверные данные в скрипте: " + Color.ANSI_YELLOW + input
                        + Color.ANSI_RED + ". Проверьте Скрипт!\n"
                        + Color.ANSI_YELLOW + "Данная строка должна содержать целое число." + Color.ANSI_RESET;
                System.out.println(message);
                throw new UncorrectedScriptException(message);
            }
        }
        return result;
    }

    private static Person MakePerson(BufferedReader reader) throws IOException, UncorrectedScriptException {
        String passportID = null; //Поле может быть null
        long weight; //Поле может быть null, Значение поля должно быть больше 0
        Scanner scanner;
        String input = "";
        while (input.isEmpty()) {
            input = reader.readLine();
            scanner = new Scanner(input);
            if (!input.isEmpty()) passportID = scanner.nextLine();

        }
        System.out.println("PassportID" + passportID);
        weight = getLong(reader);
        System.out.println("Weight" + weight);
        return new Person(weight, passportID);
    }

    private static TicketType MakeType(BufferedReader reader) throws IOException, UncorrectedScriptException {
        TicketType type = null; //Поле может быть null
        Scanner scanner;
        String input = "";
        while (input.isEmpty()) {
            input = reader.readLine();
            scanner = new Scanner(input);
            try {
                if (!input.isEmpty()) type = TicketType.valueOf(scanner.next().trim());
            } catch (IllegalArgumentException e) {
                String message = Color.ANSI_RED + "Неверные данные в скрипте: " + Color.ANSI_YELLOW + input +
                        Color.ANSI_RED + ". Проверьте Скрипт!";
                System.out.println(message);
                throw new UncorrectedScriptException(message);
            }
        }
        System.out.println("Type:" + type);
        return type;

    }
}
