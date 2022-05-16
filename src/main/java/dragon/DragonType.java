package dragon;

/**
 * Enum типов элементов коллекции*/
public enum DragonType {
    UNDERGROUND("UNDERGROUND"),
    AIR("AIR"),
    FIRE("FIRE");

    private final String description;

    DragonType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}