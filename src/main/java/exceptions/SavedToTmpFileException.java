package exceptions;

/**
 * Исключение при сохранении коллекции во временный файл
 */
@Deprecated
public class SavedToTmpFileException extends RuntimeException{
    public SavedToTmpFileException(String msg) {
        super(msg);
    }
}
