
package Server;


import Lab5.com.company.*;
import Lab5.commands.A_command;
import Lab5.commands.Command;
import com.company.Serializer;

import java.io.IOException;
import java.net.*;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class Server {
    public Server(Database database) {
        commands = new HashMap<>();
        messages = new HashMap<>();
        Server.database = database;
    }

    private static HashMap<SocketAddress, ArrayList<Command>> commands;
    private static HashMap<SocketAddress, ArrayList<String>> messages;
    private static ArrayList<String> users=new ArrayList<>();

    static Database database;

    public static void addUser(String login) {
        users.add(login);
    }

    public static void deleteUser(String login) {
        users.remove(login);
    }
    private void removeUser(SocketAddress address) {
        users.remove(address);
    }



    public static Database getDatabase() {
        return database;
    }


    public static boolean checkAuthorization(String login) {
        return users.contains(login);
    }


    public void start() throws IOException {
        DatagramSocket send_sock = new DatagramSocket(6001);
        DatagramSocket receive_sock = new DatagramSocket(7771);
        ThreadPoolExecutor executor =
                (ThreadPoolExecutor) Executors.newCachedThreadPool();
        ThreadPoolExecutor executor2 =
                (ThreadPoolExecutor) Executors.newCachedThreadPool();
        ThreadPoolExecutor executor3 =
                (ThreadPoolExecutor) Executors.newCachedThreadPool();
        executor.setCorePoolSize(1);
        executor2.setCorePoolSize(1);
        executor3.setCorePoolSize(1);
        executor.execute(() -> ReceiveData(receive_sock));
        Runnable send = () -> SendData(send_sock);
        Runnable execute = this::executeCommand;
        while (true) {
            if (executor2.getActiveCount() < 1) {
                if (!this.commands.isEmpty()) {
                    executor2.execute(execute);
                }
            }
            if (executor3.getActiveCount() < 1) {
                if (!this.messages.isEmpty()) {
                    executor3.execute(send);
                }
            }
        }
    }

    public HashMap<SocketAddress, ArrayList<String>> getMessages() {
        return messages;
    }

    public synchronized void addMessage(SocketAddress address, ArrayList<String> strings) {
        if (this.messages.containsKey(address)) {
            this.messages.get(address).addAll(strings);
        } else {
            ArrayList<String> stringss = new ArrayList<>();
            stringss.addAll(strings);
            this.messages.put(address, stringss);
        }
        System.out.println(this.messages.get(address).get(0));
    }


    public void addCommandToList(SocketAddress address, Command command) {
        if (this.commands.containsKey(address)) {
            this.commands.get(address).add(command);
        } else {
            ArrayList<Command> commandd = new ArrayList<>();
            commandd.add(command);
            this.commands.put(address, commandd);
        }
    }

    public HashMap<SocketAddress, ArrayList<Command>> getCommands() {
        return commands;
    }


    public synchronized void SendData(DatagramSocket send_sock) {
        byte[] buffer;

        if (!messages.isEmpty()) {
            SocketAddress address = null;
            ArrayList message = null;
            try {

                for (Map.Entry<SocketAddress, ArrayList<String>> set : this.messages.entrySet()) {
                    address = set.getKey();
                    message = set.getValue();
                }
                if (message.get(0).equals("Disconnect ")) {
                    return;
                } else if (message.get(0).toString().equalsIgnoreCase("Update")) {
                    buffer = Serializer.serialize(A_command.getSet());
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    send_sock.connect(address);
                    send_sock.send(packet);
                    this.messages.remove(address);
                    send_sock.disconnect();
                    return;
                }
                if (message == null) return;
                System.out.println(message.size());
                buffer = Serializer.serialize(message);
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                send_sock.connect(address);
                send_sock.send(packet);
                this.messages.remove(address);
                send_sock.disconnect();
                Thread.yield();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else return;
    }


    private void ReceiveData(DatagramSocket receive_socket) {
        while (true) {
            try {
                Command receiveCommand;
                //буфер для получения входящих данных
                byte[] buffer = new byte[65536];
                DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
                System.out.println("Ожидаем данные...");
                //Получаем данные
                receive_socket.receive(incoming);
                receive_socket.disconnect();
                receiveCommand = (Command) Serializer.deserialize(incoming.getData());
                System.out.println(Color.ANSI_BLUE + " сообщение клиента: " + receiveCommand + Color.ANSI_RESET);
                System.out.println("Адресс: " + incoming.getAddress());
                System.out.println("Порт: " + incoming.getPort());
                addCommandToList(new InetSocketAddress(incoming.getAddress(), incoming.getPort()), receiveCommand);
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("IOException 2 " + e);
            }
        }
    }


    public synchronized void executeCommand() {
        if (!commands.isEmpty()) {
            Invoker invoker = new Invoker();
            for (Map.Entry<SocketAddress, ArrayList<Command>> commands : this.commands.entrySet()) {
                if (commands.getValue() != null) {
                    for (Command command : commands.getValue()) {
                        System.out.println("execute: " + command);
                        invoker.setCommand(command);
                        try {
                            invoker.executeCommand();
                            this.addMessage(commands.getKey(), Receiver.getAnswers());
                        } catch (UncorrectedScriptException | CommandExecutionException | IOException | SQLException e) {
                            this.addMessage(commands.getKey(), new ArrayList<>(Collections.singleton(e.getMessage())));
                        }
                    }
                    this.commands.remove(commands.getKey());
                    Thread.yield();
                }
            }
        }
    }
}

