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
        return Double.compare(cave.depth, depth) == 0 && Objects.equals(numberOfTreasures, cave.numberOfTreasures);
    }

    @Override
    public int hashCode() {
        return Objects.hash(depth, numberOfTreasures);
    }
}
