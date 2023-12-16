package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.util.Util;

import jm.task.core.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoJDBCImpl implements UserDao {
    private static final Optional<Connection> connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        connection.ifPresent((_connection) -> {
            String query = """
                CREATE TABLE IF NOT EXISTS users (
                    id          serial PRIMARY KEY,
                    name        varchar(50),
                    lastname    varchar(50),
                    age         integer not null
                )
            """;

            try (PreparedStatement statement = _connection.prepareStatement(query)) {
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void dropUsersTable() {
        connection.ifPresent((_connection) -> {
            String query = "DROP TABLE IF EXISTS users";

            try (PreparedStatement statement = _connection.prepareStatement(query)) {
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void saveUser(String name, String lastName, byte age) {
        connection.ifPresent((_connection) -> {
            String query = String.format("""
                INSERT INTO users(name, lastname, age) VALUES('%s', '%s', '%d')
            """, name, lastName, age);

            try (PreparedStatement statement = _connection.prepareStatement(query)) {
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void removeUserById(long id) {
        connection.ifPresent((_connection) -> {
            String query = String.format("""
                DELETE FROM users WHERE id = '%d'
            """, id);

            try (PreparedStatement statement = _connection.prepareStatement(query)) {
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        connection.ifPresent((_connection) -> {
            String query = "SELECT * FROM users";

            try (PreparedStatement statement = _connection.prepareStatement(query)) {
                statement.executeQuery();
                ResultSet result = statement.getResultSet();
                while (result.next()) {
                    String name = result.getString("name");
                    String lastName = result.getString("lastname");
                    byte age = (byte) result.getInt("age");
                    User user = new User(name, lastName, age);
                    users.add(user);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        return users;
    }

    public void cleanUsersTable() {
        connection.ifPresent((_connection) -> {
            String query = "DELETE FROM users";

            try (PreparedStatement statement = _connection.prepareStatement(query)) {
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
