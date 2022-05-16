package jdbc.statement;

import jdbc.StatementProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.Collectors;

public final class Update extends Statement {

    Update(Connection c) {
        connection = c;
    }

    private final String QUERY_TEMPLATE =
            "UPDATE #table_name# " +
            "SET #fields# " +
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
        query = query.replace("#fields#", composeFields(property));
        query = query.replace("#criteria#", composeCriteria(property));
        return query;
    }

    private String composeFields(StatementProperty property) {
        return join(",", property.fields.stream().map(s -> s += " = ?").collect(Collectors.toList()));
    }

    private String composeCriteria(StatementProperty property) {
        if (property.criteria.size() == 0)
            return "";


        String criteria = "WHERE ";
        criteria += join(" AND ", property.criteria.stream().map(s -> s += " = ?").collect(Collectors.toList()));
        return criteria;
    }

}
