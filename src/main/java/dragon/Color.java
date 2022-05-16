package dragon;


/**
 * enum цветов элементов коллекции
 */

public enum Color {
    BLACK("BLACK"),
    BLUE("BLUE"),
    WHITE("WHITE"),
    BROWN("BROWN");

    private final String description;

    Color(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
