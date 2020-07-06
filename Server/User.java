package Server;

import java.io.Serializable;
import java.net.SocketAddress;
import java.security.SecureRandom;
import java.util.Random;

public class User implements Serializable {
    private String name;
    private String surname;
    private String mail;
    private String login;
    private String password;
    private String salt;

    public User(String name, String surname, String login, String mail, String password) {
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.login = login;
        this.password = password;
        this.salt = randomString();
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    private static String randomString() {
        int length = 16;
        Random r = new Random();
        return r.ints(48, 122)
                .filter(i -> (i < 57 || i > 65) && (i < 90 || i > 97))
                .mapToObj(i -> (char) i)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }
}
