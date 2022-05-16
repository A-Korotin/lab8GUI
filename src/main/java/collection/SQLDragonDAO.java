package collection;

import com.fasterxml.jackson.core.JsonProcessingException;
import dragon.Dragon;
import exceptions.DatabaseConnectionNotEstablishedException;
import exceptions.DatabaseException;
import io.Properties;
import net.codes.EventCode;
import net.codes.ExitCode;
import net.codes.Result;

import java.sql.SQLException;
import java.util.*;


public final class SQLDragonDAO implements DAO, Describable, Orderable {

    private final List<Dragon> collection = new LinkedList<>();

    private final jdbc.DAO.DAO<Dragon> dragonDAO = new jdbc.DAO.SQLDragonDAO();


    public SQLDragonDAO() {
        try {
            var dragons = dragonDAO.getAll();
            collection.addAll(dragons);
        } catch (SQLException e) {
            throw new DatabaseException("Ошибка базы данных");
        }
    }

    @Override
    public Result create(Properties properties) {
        Dragon dragon = new Dragon(-1, properties);

        int databaseId;
        try {
            databaseId = dragonDAO.create(dragon);
            dragon.setId(databaseId);
        } catch (DatabaseConnectionNotEstablishedException e) {
            return new Result().exitCode(ExitCode.SERVER_ERROR).eventCode(EventCode.DATABASE_CONNECTION_NOT_ESTABLISHED);
        } catch (SQLException e) {
            return new Result().exitCode(ExitCode.SERVER_ERROR).eventCode(EventCode.DATABASE_ERROR);
        }
        collection.add(dragon);

        return new Result().exitCode(ExitCode.OK);
    }

    @Override
    public Result update(int id, Properties properties) {
        Dragon dragon = new Dragon(-1, properties);

        int nRowsAffected;
        try {
            nRowsAffected = dragonDAO.update(id, dragon);
        } catch (DatabaseConnectionNotEstablishedException e) {
            return new Result().exitCode(ExitCode.SERVER_ERROR).eventCode(EventCode.DATABASE_CONNECTION_NOT_ESTABLISHED);
        } catch (SQLException e) {
            return new Result().exitCode(ExitCode.SERVER_ERROR).eventCode(EventCode.DATABASE_ERROR);
        }

        collection.forEach(d -> {
            if (d.getId() == id)
                d.update(properties);
        });

        return nRowsAffected >= 1?
                new Result().exitCode(ExitCode.OK):
                new Result().exitCode(ExitCode.INVALID_INPUT).eventCode(EventCode.ELEMENT_NOT_FOUND);
    }

    @Override
    public Result delete(int id) {

        int nRowsAffected;
        try {
            nRowsAffected = dragonDAO.delete(id);
        } catch (DatabaseConnectionNotEstablishedException e) {
            return new Result().exitCode(ExitCode.SERVER_ERROR).eventCode(EventCode.DATABASE_CONNECTION_NOT_ESTABLISHED);
        } catch (SQLException e) {
            return new Result().exitCode(ExitCode.SERVER_ERROR).eventCode(EventCode.DATABASE_ERROR);
        }

        collection.removeIf(d -> d.getId() == id);

        return nRowsAffected >= 1?
                new Result().exitCode(ExitCode.OK):
                new Result().exitCode(ExitCode.INVALID_INPUT).eventCode(EventCode.ELEMENT_NOT_FOUND);
    }

    @Override
    public Dragon get(int id) {
        Optional<Dragon> dragon = collection.stream().filter(d->d.getId() == id).findFirst();
        return dragon.orElse(null);
    }

    @Override
    public List<Dragon> getAll() {
        return new ArrayList<>(collection);
    }

    @Override
    public Result clear() {
        try {
            dragonDAO.clear();
            collection.clear();
        } catch (DatabaseConnectionNotEstablishedException e) {
            return new Result().exitCode(ExitCode.SERVER_ERROR).eventCode(EventCode.DATABASE_CONNECTION_NOT_ESTABLISHED);
        } catch (SQLException e) {
            return new Result().exitCode(ExitCode.SERVER_ERROR).eventCode(EventCode.DATABASE_ERROR);
        }
        return new Result().exitCode(ExitCode.OK);
    }

    @Override
    public String description() throws JsonProcessingException {
        return "SQL DAO";
    }

    @Override
    public String info() {
        return "SQL DAO {" + System.lineSeparator() +
                "type = postgreSQL," + System.lineSeparator() +
                "size = " + collection.size() + System.lineSeparator() +
                "}";
    }

    @Override
    public Result sort() {
        Collections.sort(collection);
        return new Result().exitCode(ExitCode.OK);
    }
}
