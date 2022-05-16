package jdbc.DAO;

import dragon.Coordinates;
import jdbc.StatementProperty;
import jdbc.statement.Statement;
import jdbc.statement.StatementFactory;
import jdbc.statement.StatementType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

final class SQLCoordinateDAO implements DAO<Coordinates>{
    private final String TABLE_NAME = "coordinate";

    @Override
    public int create(Coordinates element) throws SQLException {
        StatementProperty property = new StatementProperty.Builder()
                .tableName(TABLE_NAME)
                .fields("x", "y")
                .valuesSetter(s -> {
                    s.setFloat(1, element.getX());
                    s.setInt(2, element.getY());
                })
                .build();

        try (Statement s= StatementFactory.getStatement(StatementType.INSERT)) {
            var set = s.composePreparedStatement(property).executeQuery();
            set.next();
            return set.getInt("id");
        }

    }

    @Override
    public int update(int id, Coordinates element) throws SQLException {
        StatementProperty property = new StatementProperty.Builder()
                .tableName(TABLE_NAME)
                .fields("x", "y")
                .criteria("id")
                .valuesSetter(s -> {
                    s.setFloat(1, element.getX());
                    s.setInt(2, element.getY());
                    s.setInt(3, id);
                })
                .build();
        try(Statement s = StatementFactory.getStatement(StatementType.UPDATE)) {
            return s.composePreparedStatement(property).executeUpdate();
        }

    }

    @Override
    public int delete(int id) throws SQLException {
        StatementProperty property = new StatementProperty.Builder()
                .tableName(TABLE_NAME)
                .criteria("id")
                .valuesSetter(s->s.setInt(1, id))
                .build();
        try(Statement s = StatementFactory.getStatement(StatementType.DELETE)) {
            return s.composePreparedStatement(property).executeUpdate();
        }
    }

    @Override
    public Coordinates get(int id) throws SQLException {
        StatementProperty property = new StatementProperty.Builder()
                .tableName(TABLE_NAME)
                .criteria("id")
                .valuesSetter(s-> s.setInt(1, id))
                .build();

        try(Statement s = StatementFactory.getStatement(StatementType.SELECT)) {
            var set = s.composePreparedStatement(property).executeQuery();
            set.next();
            return parse(set);
        }
    }

    @Override
    public List<Coordinates> getAll() throws SQLException {
        List<Coordinates> output = new ArrayList<>();
        StatementProperty property = new StatementProperty.Builder()
                .tableName(TABLE_NAME)
                .build();

        try(Statement s = StatementFactory.getStatement(StatementType.SELECT)) {
            var set = s.composePreparedStatement(property).executeQuery();

            while (set.next())
                output.add(parse(set));

            return output;
        }
    }

    @Override
    public int clear() throws SQLException {
        StatementProperty property = new StatementProperty.Builder()
                .tableName(TABLE_NAME)
                .build();

        try(Statement s = StatementFactory.getStatement(StatementType.DELETE)) {
            return s.composePreparedStatement(property).executeUpdate();
        }
    }

    private Coordinates parse(ResultSet row) throws SQLException {
        return new Coordinates(row.getFloat("x"), row.getInt("y"));
    }
}
