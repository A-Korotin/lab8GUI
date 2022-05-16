package exceptions;

public class RequiredFieldNotSetException extends RuntimeException {
    public RequiredFieldNotSetException(String msg) {
        super("Обязательное поле %s не установлено".formatted(msg));
    }
}
