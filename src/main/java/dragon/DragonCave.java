package dragon;


import collection.DragonDAO;
import com.fasterxml.jackson.annotation.JsonInclude;

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
}
