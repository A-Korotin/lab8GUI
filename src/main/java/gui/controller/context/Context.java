package gui.controller.context;

import locales.Locale;
import net.Client;
import net.auth.User;

import java.io.IOException;

public class Context {
    static {
        try {
            client = new Client("localhost", 4444);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Locale locale = new Locale("default");
    public static Client client;
    public static User user = new User("admin", "");
}
