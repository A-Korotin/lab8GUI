package gui.controller.context;

import locale.Locale;
import net.Client;
import net.auth.User;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    public static ExecutorService pool = Executors.newFixedThreadPool(1);
}
