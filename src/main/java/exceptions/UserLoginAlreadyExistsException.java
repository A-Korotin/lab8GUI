package exceptions;

public class UserLoginAlreadyExistsException extends RuntimeException{
    public UserLoginAlreadyExistsException(String msg) {
        super("Логин '%s' уже существует".formatted(msg));
    }
}
