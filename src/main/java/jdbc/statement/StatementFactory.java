package jdbc.statement;

import exceptions.DatabaseConnectionNotEstablishedException;
import jdbc.ConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;


public final class StatementFactory {

    private StatementFactory() {}

    public static Statement getStatement(StatementType type) throws DatabaseConnectionNotEstablishedException {
        Connection c;
        try {
            c = ConnectionPool.getConnection();
        } catch (SQLException e) {
            throw new DatabaseConnectionNotEstablishedException(e.getMessage());
        }
        return switch (type) {
            case DELETE -> new Delete(c);
            case INSERT -> new Insert(c);
            case SELECT -> new Select(c);
            case UPDATE -> new Update(c);
        };
    }
}
