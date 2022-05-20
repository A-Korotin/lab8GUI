package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public final class ConnectionPool {
    //private final static BasicDataSource pool = new BasicDataSource();

    private static final String URL = "jdbc:postgresql://localhost:2222/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD;
    static {
//        pool.setUrl("jdbc:postgresql://localhost:2222/postgres");
//        pool.setUsername("postgres");
//        pool.setPassword(getPassDEBUG());
//        pool.setMinIdle(5);
//        pool.setMaxIdle(10);
//        pool.setMaxOpenPreparedStatements(10);
//        pool.setTimeBetweenEvictionRunsMillis(5 * 1000);
        PASSWORD = getPassDEBUG();
    }

    public static Connection getConnection() throws SQLException {
        //return pool.getConnection();
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    private static String getPass() {
        System.out.println("Введите пароль подключения к БД");
        return new String(System.console().readPassword());
    }

    private static String getPassDEBUG() {
        return "12345";
    }
}
