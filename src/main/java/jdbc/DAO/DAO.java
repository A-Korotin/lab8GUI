package jdbc.DAO;

import java.sql.SQLException;
import java.util.List;

public interface DAO<T> {

    int create(T element) throws SQLException;

    int update(int id, T element) throws SQLException;

    int delete(int id) throws SQLException;

    T get(int id) throws SQLException;

    List<T> getAll() throws SQLException;

    int clear() throws SQLException;
}
