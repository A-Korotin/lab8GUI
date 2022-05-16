package dragon;


/**
 * Enum типов характера элементов коллекции*/
public enum DragonCharacter {
    CUNNING("CUNNING"),
    GOOD("GOOD"),
    CHAOTIC("CHAOTIC"),
    CHAOTIC_EVIL("CHAOTIC_EVIL"),
    FICKLE("FICKLE");

    private final String description;

    DragonCharacter(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean compareCharacter(DragonCharacter character) {
        return description.length() > character.getDescription().length();
    }

    public static int compare(DragonCharacter obj1, DragonCharacter obj2) {
        int len1 = obj1 == null ? -1 : obj1.getDescription().length();
        int len2 = obj2 == null ? -1 : obj2.getDescription().length();
        return len1 - len2;
    }

    public static boolean compareBoolean(DragonCharacter obj1, DragonCharacter obj2) {
        int len1 = obj1 == null ? -1 : obj1.getDescription().length();
        int len2 = obj2 == null ? -1 : obj2.getDescription().length();
        return len1 - len2 > 0;
    }
}