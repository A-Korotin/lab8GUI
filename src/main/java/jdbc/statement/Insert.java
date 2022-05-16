package jdbc.statement;

import jdbc.StatementProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.StringJoiner;

public final class Insert extends Statement {

    Insert(final Connection c) {
        connection = c;
    }

    private final String QUERY_TEMPLATE =
            "INSERT INTO #table_name# " +
            "(#fields#) " +
            "VALUES (#values#) RETURNING id";

    @Override
    public PreparedStatement composePreparedStatement(StatementProperty property) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(concreteQuery(property));
        property.valuesSetter.accept(statement);
        return statement;
    }


    private String concreteQuery(StatementProperty property) {
        String query = QUERY_TEMPLATE;
        query = query.replace("#table_name#", join(",", property.tableName));
        query = query.replace("#fields#", join(",", property.fields));
        query = query.replace("#values#", joinUnknownParams(",", property.fields.size()));
        return query;
    }


    private String joinUnknownParams(String delimiter, int amount) {
        StringJoiner joiner = new StringJoiner(delimiter);
        for (int i = 0; i < amount; i++)
            joiner.add("?");
        return joiner.toString();
    }
}
