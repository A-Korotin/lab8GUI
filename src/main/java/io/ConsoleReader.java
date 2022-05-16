package io;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Класс для чтения ввода с консоли, наследуется от абстрактного класса io.InputReader*/
public final class ConsoleReader extends InputReader {

    {
        askForInput = true;
    }

    public String getRawInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine().trim();
    }
    /**
     * Метод получения данных с консоли
     * @return out - Список списков, считанный с консоли
     * */
    @Override
    public List<String> getInput() {
       String line = getRawInput();
        List<String> output = new ArrayList<>();
        output.add(line);
        return output;
    }
}

