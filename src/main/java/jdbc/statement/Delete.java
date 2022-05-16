package jdbc.statement;

import jdbc.StatementProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.Collectors;

public final class Delete extends Statement {

    Delete(Connection c) {
        connection = c;
    }

    private final String QUERY_TEMPLATE =
            "DELETE FROM #table_name# " +
            "#criteria#";

    @Override
    public PreparedStatement composePreparedStatement(StatementProperty property) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(concreteQuery(property));
        property.valuesSetter.accept(statement);
        return statement;
    }

    private String concreteQuery(StatementProperty property) {
        String query = QUERY_TEMPLATE;
        query = query.replace("#table_name#", join(",", property.tableName));
        query = query.replace("#criteria#", composeCriteria(property));
        return query;
    }

    private String composeCriteria(StatementProperty property) {
        if (property.criteria.size() == 0)
            return "";

        String criteria = "WHERE ";
        criteria += join(" AND ", property.criteria.stream().map(s -> s += " = ?").collect(Collectors.toList()));
        return criteria;
    }

}
