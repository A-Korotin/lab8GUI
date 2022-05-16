package exceptions;

/**
 * Исключение неверного значения
 */
public final class InvalidValueException extends Exception {
    public InvalidValueException(String msg) {
        super(msg);
    }
}
