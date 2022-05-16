package exceptions;

public class InfiniteLoopException extends RuntimeException {
    public InfiniteLoopException() {
        super("Обнаружена рекурсия скриптов.");
    }
}
