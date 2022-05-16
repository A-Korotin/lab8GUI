package io.request;

import dragon.Color;
import dragon.DragonCharacter;
import dragon.DragonType;
import exceptions.ProgramExitException;
import io.Properties;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.function.Function;

/**
 * Класс, запрашивающий данные от пользователя с консоли
*/
public final class ConsoleRequester {

    private InputStream inputStream = System.in;
    private PrintStream printStream = System.out;

    public void setInputStream(InputStream stream) {
        inputStream = stream;
    }

    public void setPrintStream(PrintStream stream) {
        printStream = stream;
    }


    private <T> T request(Predicate<T> validationFunc, Function<String, T> convertFunc, String startMsg) {
        Scanner scanner = new Scanner(inputStream);

        boolean validInput = false;
        String input;
        T received = null;

        printStream.print(startMsg);
        while(!validInput) {
            try {
                input = scanner.nextLine().trim();
                received = convertFunc.apply(input);

                if(!(validInput = validationFunc.test(received)))
                    printStream.println("Неверный ввод");

            } catch (NoSuchElementException e) {
                throw new ProgramExitException("Завершение программы...");
            }
            catch (RuntimeException e) {
                printStream.println("Неверный ввод");
            }
        }
        return received;
    }

    public Properties requestProperties() {
        Properties p = new Properties();

        p.name = request(input -> !input.isEmpty(), input -> input,
                "Введите имя, не может быть пустым: ");

        p.xCoord = request(input -> true, Float::parseFloat,
                "Введите координату X пещеры дракона, Float: ");

        p.yCoord = request(input -> input <= 998, Integer::parseInt,
                "Введите координату Y пещеры дракона, Integer <= 998: ");

        p.age = request(input -> input > 0, Long::parseLong,
                "Введите возраст дракона, Long >0: ");

        p.color = request(input -> true, input -> input.equalsIgnoreCase("null") ? null : Color.valueOf(input.toUpperCase()),
                "Введите цвет дракона BLACK, BLUE, WHITE, BROWN, null: ");

        p.type = request(input -> true, input -> DragonType.valueOf(input.toUpperCase()),
                "Введите тип дракона UNDERGROUND, AIR, FIRE: ");

        p.character = request(input -> true, input -> input.equalsIgnoreCase("null") ? null: DragonCharacter.valueOf(input.toUpperCase()),
                "Введите характер дракона CUNNING, GOOD, CHAOTIC, CHAOTIC_EVIL, FICKLE, null: ");

        p.depth = request(input -> true, Double::parseDouble,
                "Введите глубину пещеры дракона, Double: ");

        p.numberOfTreasures = request(input -> input == null || input > 0, input -> input.equalsIgnoreCase("null") ? null : Integer.parseInt(input),
                "Введите количество сокровищ в пещере дракона, Integer >0 или null: ");

        return p;
    }
}
