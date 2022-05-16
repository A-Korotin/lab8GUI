package log;

import java.util.ArrayList;
import java.util.List;
/**
* Класс для записи запросов команд в массив (создан для команды history)*/
public final class Logger {
    private Logger() {}

    private static int capacity = 6;
    private static final List<String> history = new ArrayList<>();
    /**
    * Метод добавления имени команды в массив
    * @param command - имя команды
    * */
    public static void log(String command){
        history.add(0,command);
        if (history.size() > capacity) {
            history.remove(capacity);
        }
    }
    /**
     * Метод возвращения копии массива
     * @return new ArrayList<>(history) - копия массива с именами команд
     * */
    public static List<String> getAll(){
        return new ArrayList<>(history);
    }

    public static void setCapacity(int capacity) {
        Logger.capacity = capacity;
    }
}
