package main;

import net.Server;

import java.io.IOException;
import java.net.BindException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("HELLO!");
        try {
            Class.forName("net.Request").getClassLoader().loadClass("net.Request");
            Class.forName("jdbc.UserManager").getClassLoader().loadClass("jdbc.UserManager");
            Server server = new Server("localhost", 4444);
            server.run();
        } catch (BindException e) {
            System.out.printf("Не удалось запустить сервер (%s)", e.getMessage());
        }

    }
}


