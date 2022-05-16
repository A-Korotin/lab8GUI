package collection;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import dragon.Dragon;
import io.Properties;

import json.Json;
import net.codes.EventCode;
import net.codes.ExitCode;
import net.codes.Result;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Класс, который имплементируется от collection.DAO. В нём мы реализуем методы для работы с коллекцией и инициализируем саму коллекцию
 */
@Deprecated
public final class DragonDAO implements DAO, Describable, Orderable {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm:ss")
    private LocalDateTime initDateTime;

    private int availableId = 1;

    private List<Dragon> collection = new LinkedList<>();

    public DragonDAO() {
        initDateTime = LocalDateTime.now();
    }

    public DragonDAO(List<Dragon> collection) {
        this();
        this.collection = collection;
    }

    /**
     * Метод добавления элемента в коллекцию
     * @param properties - свойства элемента
     * */
    @Override
    public Result create(Properties properties) {
        collection.add(new Dragon(availableId++, properties));
        return new Result().exitCode(ExitCode.OK);
    }
    /**
     * Метод обновления элемента в коллекции по его id
     * @param properties - свойства элемента
     * @param id - id элемента, который пользователь хочет обновить
     * */
    @Override
    public Result update(int id, Properties properties) {
        Result result = new Result();
        for(Dragon dragon1 : collection){
            if (id == dragon1.getId()) {
                dragon1.update(properties);
                return result.exitCode(ExitCode.OK);
            }
        }
        return result.exitCode(ExitCode.INVALID_INPUT).eventCode(EventCode.ELEMENT_NOT_FOUND);
    }
    /**
     * Метод удаления элемента из коллекции по его id
     * @param id - id элемента, который пользователь хочет удалить
     * */
    @Override
    public Result delete(int id) {
        Result result = new Result();
        if (collection.removeIf(dragon -> dragon.getId() == id))
            return result.exitCode(ExitCode.OK);
        return result.exitCode(ExitCode.INVALID_INPUT).eventCode(EventCode.ELEMENT_NOT_FOUND);
    }
    /**
     * Метод получения элемента из коллекции по его id
     * @param id - id элемента, который пользователь хочет получить
     * @return dragon - элемент коллекции
     * */
    @Override
    public Dragon get(int id) {
        for(Dragon dragon : collection){
            if (dragon.getId() == id) {
                return dragon;
            }
        }
        return null;
    }
    /**
     * Метод получения всей коллекции
     * @return outputCollection - копия коллекции
     * */
    @JsonProperty("collection")
    @Override
    public List<Dragon> getAll(){
        List<Dragon> outputCollection = new LinkedList<>();
        outputCollection.addAll(collection);
        return outputCollection;
    }
    /**
     * Метод очистки всей коллекции
     * */
    @Override
    public Result clear() {
        collection.clear();
        return new Result().exitCode(ExitCode.OK);
    }
    /**
     * Метод возвращения информации о коллекции
     * @return output - информация о коллекции
     * */
    @Override
    public String description() throws JsonProcessingException {
        JsonNode node = Json.toJson(this);
        return Json.stringRepresentation(node, true);
    }
    /**
     * Метод сортировки коллекции
     * */
    @Override
    public Result sort() {
        Collections.sort(collection);
        return new Result().exitCode(ExitCode.OK);
    }

    @Override
    public String info() {
        return "Collection {" + System.lineSeparator() +
                "\tinit date: " + initDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm:ss")) + "," + System.lineSeparator() +
                "\ttype: " + "LinkedList" + "," + System.lineSeparator() +
                "\tsize: " + collection.size() + "," + System.lineSeparator() +
                "}";
    }

    public LocalDateTime getInitDateTime() {
        return initDateTime;
    }

    public void setInitDateTime(LocalDateTime initDateTime) {
        this.initDateTime = initDateTime;
    }

    public int getAvailableId() {
        return availableId;
    }

    public void setAvailableId(int availableId) {
        this.availableId = availableId;
    }

    public void setCollection(List<Dragon> collection) {
        this.collection = collection;
    }
}
