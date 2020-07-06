package Client;

import Lab5.com.company.Color;
import Lab5.com.company.PersonalArea;
import Lab5.com.company.Readder;
import Lab5.com.company.Ticket;
import Lab5.commands.*;
import Server.Command_Disconnect;
import com.company.Serializer;


import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;


public class Client {
    private static DatagramChannel channel;
    private static String login = "";
    private static ByteBuffer receiveData;
    private static InetAddress serverIP;

    public Client() throws IOException {
        channel = DatagramChannel.open();
        channel.configureBlocking(false);
        channel.bind(new InetSocketAddress((int) (Math.random() * 6000) + 2000));
        receiveData = ByteBuffer.allocate(1000000);
        serverIP = InetAddress.getByName("127.0.0.1");
    }

    public static void setServerIP(InetAddress serverIP) {
        Client.serverIP = serverIP;
    }

    public static String getLogin() {
        return login;
    }

    public static void setLogin(String login) {
        Client.login = login;
    }

    private static void checkConnect() throws IOException {
        int i = 0;
        while (i < 5) {
            if (!serverIP.isReachable(1000)) {
                System.out.println(Color.ANSI_RED + "Сервер временно недоступен!" + Color.ANSI_RESET);
                i++;
                continue;
            } else return;
        }
        System.exit(-1);
    }

    public static int SendData(Command command) {
        try {
            checkConnect();
            channel.connect(new InetSocketAddress(serverIP, 7771));
            System.out.println("Готовим запрос к отправке =) ");
            if (command instanceof Command_Exit) {
                channel.send(ByteBuffer.wrap(Serializer.serialize(new Command_Disconnect())), new InetSocketAddress(serverIP, 7771));
                System.exit(0);
            } else if (command instanceof Command_Save) {
                System.out.println(Color.ANSI_BLUE + "У вас нет доступа к этой команде)" + Color.ANSI_RESET);
                channel.disconnect();
                return 1;
            }

            channel.send(ByteBuffer.wrap(Serializer.serialize(command)), new InetSocketAddress(serverIP, 7771));
            System.out.println("Отправили");
            channel.disconnect();
            return 1;
        } catch (IOException e) {
            System.err.println("IOException  " + e);
            return -1;
        }
    }

    public static ArrayList<String> ReceiveData() {

        ArrayList<String> answer = new ArrayList<>();
            try {
                Thread.sleep(1000);
                receiveData.clear();
                System.out.println("Ожидаем ответ...");
                Thread.yield();
                SocketAddress from = channel.receive(receiveData);
                answer = new ArrayList<>();
                if (from != null) {
                    receiveData.flip();
                    if (Serializer.deserialize(receiveData.array()) instanceof ArrayList) {
                        answer = (ArrayList<String>) Serializer.deserialize(receiveData.array());
                    } else {
                        A_command.setSet((TreeSet<Ticket>) Serializer.deserialize(receiveData.array()));
                    }
                }
            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                System.err.println("IOException Receive " + e);
            }

        return answer;
    }

}

