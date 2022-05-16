package jdbc.statement;

import jdbc.StatementProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.StringJoiner;

public abstract class Statement implements AutoCloseable {
    protected Connection connection;

    public abstract PreparedStatement composePreparedStatement(StatementProperty property) throws SQLException;

    protected final String join(String delimiter, List<String> source) {
        StringJoiner joiner = new StringJoiner(delimiter);
        source.forEach(joiner::add);
        return joiner.toString();
    }

    @Override
    public void close() throws SQLException {
        connection.close();
    }
}
