package dragon;


/**
 * Класс координат элементов коллекции
 */

public class Coordinates {
    public Coordinates(Float x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates() {}

    private Float x; //Поле не может быть null
    private Integer y; //Максимальное значение поля: 998, Поле не может быть null

    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "dragon.Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
