package exceptions;

/**
 * Исключение для выхода из основного цикла программы
 */
public final class ProgramExitException extends RuntimeException {
    public ProgramExitException(String msg) {
        super(msg);
    }
}
