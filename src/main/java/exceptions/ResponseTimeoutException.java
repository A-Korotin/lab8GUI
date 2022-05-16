package exceptions;

import java.io.IOException;

public class ResponseTimeoutException extends IOException {
    public ResponseTimeoutException() {
        super("Превышено время ожидания ответа сервера. Повторите попытку позже");
    }
}
