package io;

import dragon.Color;
import dragon.DragonCharacter;
import dragon.DragonType;
import exceptions.InvalidValueException;

import java.util.List;

/**
* Класс присваивания полям элементов значений, полученных с консоли или из файла
*/
public final class Properties {
    public String name;
    public Float xCoord;
    public Integer yCoord;
    public Long age;
    public Color color;
    public DragonType type;
    public DragonCharacter character;
    public double depth;
    public Integer numberOfTreasures;
    public String creator_name;

    public static Properties parseProperties(List<String> input, int indexShift) throws InvalidValueException{
        Properties properties = new Properties();

        try {
            properties.name = input.get(0 + indexShift);
            properties.xCoord = Float.parseFloat(input.get(1 + indexShift));
            properties.yCoord = Integer.parseInt(input.get(2 + indexShift));
            properties.age = Long.parseLong(input.get(3 + indexShift));
            properties.color = input.get(4 + indexShift).equals("null") ? null: Color.valueOf(input.get(4 + indexShift).toUpperCase());
            properties.type = DragonType.valueOf(input.get(5 + indexShift).toUpperCase());
            properties.character = input.get(6 + indexShift).equals("null") ? null : DragonCharacter.valueOf(input.get(6 + indexShift).toUpperCase());
            properties.depth = Double.parseDouble(input.get(7 + indexShift));
            properties.numberOfTreasures = input.get(8 + indexShift).equals("null") ? null: Integer.parseInt(input.get(8 + indexShift));
        } catch (RuntimeException e) {
            throw new InvalidValueException("Ошибка. Типы данных несовместимы.");
        }

        if (properties.name.isEmpty())
            throw new InvalidValueException("Ошибка. Параметр ИМЯ не может быть пустым");

        if (properties.yCoord > 998)
            throw new InvalidValueException("Ошибка. Параметр КООРДИНАТА_Y не может быть >998 (Получено %d)".formatted(properties.yCoord));

        if (properties.age <= 0)
            throw new InvalidValueException("Ошибка. Параметр ВОЗРАСТ не может быть <= 0 (Получено %d)".formatted(properties.age));

        if (properties.numberOfTreasures != null && properties.numberOfTreasures <= 0)
            throw new InvalidValueException("Ошибка. Параметр КОЛИЧЕСТВО_СОКРОВИЩ_В_ПЕЩЕРЕ не может быть <= 0 (Получено %d)".formatted(properties.numberOfTreasures));

        return properties;
    }

    @Override
    public String toString() {
        return "Properties{" +
                "name='" + name + '\'' +
                ", xCoord=" + xCoord +
                ", yCoord=" + yCoord +
                ", age=" + age +
                ", color=" + color +
                ", type=" + type +
                ", character=" + character +
                ", depth=" + depth +
                ", numberOfTreasures=" + numberOfTreasures +
                ", creator_name='" + creator_name + '\'' +
                '}';
    }
}