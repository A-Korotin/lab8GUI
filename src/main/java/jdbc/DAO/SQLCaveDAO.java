package jdbc.DAO;

import dragon.DragonCave;
import jdbc.StatementProperty;
import jdbc.statement.Statement;
import jdbc.statement.StatementFactory;
import jdbc.statement.StatementType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

final class SQLCaveDAO implements DAO<DragonCave> {

    private final String TABLE_NAME = "cave";

    @Override
    public int create(final DragonCave element) throws SQLException {
        StatementProperty property = new StatementProperty.Builder()
                .tableName(TABLE_NAME)
                .fields("depth", "n_treasures")
                .valuesSetter(s -> {
                    s.setDouble(1, element.getDepth());

                    s.setObject(2, element.getNumberOfTreasures());
                })
                .build();
        try(Statement s = StatementFactory.getStatement(StatementType.INSERT)) {
            var set = s.composePreparedStatement(property).executeQuery();
            set.next();
            return set.getInt("id");
        }
    }

    @Override
    public int update(int id, final DragonCave element) throws SQLException {
        StatementProperty property = new StatementProperty.Builder()
                .tableName(TABLE_NAME)
                .fields("depth", "n_treasures")
                .criteria("id")
                .valuesSetter(s -> {
                    s.setDouble(1, element.getDepth());
                    s.setObject(2, element.getNumberOfTreasures());
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
                .valuesSetter(s -> s.setInt(1, id))
                .build();

        try(Statement s = StatementFactory.getStatement(StatementType.DELETE)) {
            return s.composePreparedStatement(property).executeUpdate();
        }
    }

    @Override
    public DragonCave get(int id) throws SQLException {
        StatementProperty property = new StatementProperty.Builder()
                .tableName(TABLE_NAME)
                .criteria("id")
                .valuesSetter(s -> s.setInt(1, id))
                .build();

        try(Statement s = StatementFactory.getStatement(StatementType.SELECT)) {
            var set = s.composePreparedStatement(property).executeQuery();
            set.next();
            return parse(set);
        }

    }

    @Override
    public List<DragonCave> getAll() throws SQLException {
        List<DragonCave> output = new ArrayList<>();

        StatementProperty property = new StatementProperty.Builder()
                .tableName(TABLE_NAME)
                .build();


        try(Statement s = StatementFactory.getStatement(StatementType.SELECT)) {
             var set = s.composePreparedStatement(property).executeQuery();
            while(set.next())
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

    private DragonCave parse(final ResultSet row) throws SQLException {
        return new DragonCave(row.getDouble("depth"), (Integer) row.getObject("n_treasures"));
    }
}
