package exceptions;

import java.sql.SQLException;

public class DatabaseConnectionNotEstablishedException extends SQLException {

    public DatabaseConnectionNotEstablishedException(String msg) {
        super("Подключение к базе данных не было успешно установлено (%s)".formatted(msg));
    }
}
