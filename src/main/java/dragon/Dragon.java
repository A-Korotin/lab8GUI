package dragon;


import collection.Describable;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import io.Properties;
import json.Json;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


/**
Класс элементов коллекции
 */
public class Dragon implements Comparable<Dragon>, Describable {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически

    private String name; //Поле не может быть null, Строка не может быть пустой

    private Coordinates coordinates; //Поле не может быть null

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    private Long age; //Значение поля должно быть больше 0, Поле не может быть null

    @JsonInclude(JsonInclude.Include.ALWAYS)
    private Color color; //Поле может быть null

    private DragonType type; //Поле не может быть null

    @JsonInclude(JsonInclude.Include.ALWAYS)
    private DragonCharacter character; //Поле может быть null

    private DragonCave cave; //Поле не может быть null

    private String creator;

    public Dragon() {}

    public Dragon(int id, String name, Coordinates coordinates, LocalDate creationDate, Long age, Color color, DragonType type, DragonCharacter character, DragonCave cave) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.age = age;
        this.color = color;
        this.type = type;
        this.character = character;
        this.cave = cave;
    }

    public Dragon(int id, Properties properties) {
        this.id = id;
        name = properties.name;
        coordinates = new Coordinates(properties.xCoord, properties.yCoord);
        creationDate = LocalDate.now();
        age = properties.age;
        color = properties.color;
        type = properties.type;
        character = properties.character;
        cave = new DragonCave(properties.depth, properties.numberOfTreasures);
        creator = properties.creator_name;
    }

    public String description() throws JsonProcessingException {
        JsonNode node = Json.toJson(this);
        return Json.stringRepresentation(node, true);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public DragonType getType() {
        return type;
    }

    public void setType(DragonType type) {
        this.type = type;
    }

    public DragonCharacter getCharacter() {
        return character;
    }

    public void setCharacter(DragonCharacter character) {
        this.character = character;
    }

    public DragonCave getCave() {
        return cave;
    }

    public void setCave(DragonCave cave) {
        this.cave = cave;
    }

    public String getCreatorName() {
        return creator;
    }

    public void setCreatorName(String creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "dragon.Dragon{" + System.lineSeparator() +
                "id= " + id + System.lineSeparator() +
                "name= '" + name + '\'' + System.lineSeparator() +
                "coordinates= " + coordinates + System.lineSeparator() +
                "creationDate= " + creationDate.format(DateTimeFormatter.ofPattern("dd.MM.uuuu")) + System.lineSeparator() +
                "age= " + age + System.lineSeparator() +
                "color= " + color + System.lineSeparator() +
                "type= " + type + System.lineSeparator() +
                "character= " + character + System.lineSeparator() +
                "cave= " + cave + System.lineSeparator() +
                "creator= " + creator + System.lineSeparator() +
                '}';
    }

    @Override
    public int compareTo(Dragon dragon) {
        return Long.compare(age, dragon.age);
    }

    public void update(Properties properties){
        name = properties.name;
        coordinates = new Coordinates(properties.xCoord, properties.yCoord);
        age = properties.age;
        color = properties.color;
        type = properties.type;
        character = properties.character;
        cave = new DragonCave(properties.depth, properties.numberOfTreasures);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dragon dragon = (Dragon) o;
        return name.equals(dragon.name) && coordinates.equals(dragon.coordinates) && creationDate.equals(dragon.creationDate) && age.equals(dragon.age) && color == dragon.color && type == dragon.type && character == dragon.character && cave.equals(dragon.cave) && creator.equals(dragon.creator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, coordinates, creationDate, age, color, type, character, cave, creator);
    }
}



