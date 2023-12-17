package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class Util {
    private static final String DB_NAME = "teletubbies";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/" + DB_NAME;
    private static final String DB_DRIVER = "org.postgresql.Driver";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    static {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static Optional<Connection> connection = Optional.empty();

    static {
        Runnable closeConnection = () -> connection.ifPresent(c -> {
            try {
                c.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        Runtime.getRuntime().addShutdownHook(new Thread(closeConnection));
    }

    public static Optional<Connection> getConnection() {
        if (connection.isEmpty()) {
            try {
                connection = Optional.of(DriverManager.getConnection(DB_URL, USER, PASSWORD));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return connection;
    }
}