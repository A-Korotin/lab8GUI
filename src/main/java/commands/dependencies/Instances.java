package commands.dependencies;


import collection.DAO;
import collection.DragonDAO;
import io.*;
import io.request.ConsoleRequester;

import java.util.ArrayList;
import java.util.List;

public final class Instances {
    public DAO dao = new DragonDAO();
    public InputReader consoleReader = new ConsoleReader();
    public ConsoleRequester consoleRequester = new ConsoleRequester();
    public InputReader fileReader = new FileReader();
    public static List<String> filePathChain = new ArrayList<>();

}
