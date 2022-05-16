package io;

import com.fasterxml.jackson.core.JsonProcessingException;
import dragon.Dragon;

import java.util.List;

/**
 Класс для вывода информации в консоль. Имплементируется от интерфейса io.OutPutter*/
public final class ConsoleOutput implements OutPutter {
    public void output(String msg) {
        System.out.println(msg);
    }

    @Override
    public void output(Dragon element) {
        try {
            System.out.println(element.description());
        } catch (JsonProcessingException e) {
            System.out.println("Не удалось получить описание элемента");
        }
    }

    @Override
    public <T extends Number>void output(T t) {
        System.out.println(t);
    }

    @Override
    public List<String> compound() {
        return null;
    }
}