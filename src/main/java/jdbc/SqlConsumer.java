package jdbc;

import java.sql.SQLException;
import java.util.function.Consumer;

@FunctionalInterface
public interface SqlConsumer<T> extends Consumer<T> {
    @Override
    default void accept(final T element) {
        try {
            acceptThrows(element);
        } catch (final SQLException e) {
            e.printStackTrace();
        }

    }

    void acceptThrows(T element) throws SQLException;
}

