package dragon;


import collection.DragonDAO;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

/**
 * Класс пещеры элементов коллекции*/
public class DragonCave {

    public DragonCave(double depth, Integer numberOfTreasures) {
        this.depth = depth;
        this.numberOfTreasures = numberOfTreasures;
    }

    public DragonCave() {}

    private double depth;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    private Integer numberOfTreasures; //Поле может быть null, Значение поля должно быть больше 0

    public double getDepth() {
        return depth;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }

    public Integer getNumberOfTreasures() {
        return numberOfTreasures;
    }

    public void setNumberOfTreasures(Integer numberOfTreasures) {
        this.numberOfTreasures = numberOfTreasures;
    }

    @Override
    public String toString() {
        return "dragon.DragonCave{" +
                "depth=" + depth +
                ", numberOfTreasures=" + numberOfTreasures +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DragonCave cave = (DragonCave) o;

        if (Double.compare(cave.depth, depth) != 0) return false;
        return numberOfTreasures != null ? numberOfTreasures.equals(cave.numberOfTreasures) : cave.numberOfTreasures == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(depth);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + (numberOfTreasures != null ? numberOfTreasures.hashCode() : 0);
        return result;
    }
}
