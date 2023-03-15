package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS user (
                      `id` INT NOT NULL AUTO_INCREMENT,
                      `name` VARCHAR(45) NOT NULL,
                      `lastname` VARCHAR(45) NOT NULL,
                      `age` INT NULL,
                      PRIMARY KEY (`id`));""");
        } catch (SQLException e) {
            System.err.println("Ошибка создания таблицы!");
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS user");
        } catch (SQLException e) {
            System.err.println("Ошибка удаления таблицы \"user\"");
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO user (name, lastname, age) VALUES(?, ?, ?)")) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Ошибка сохранения пользователя");
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM user WHERE id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка удаления пользователя: " + id);
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM user");
            while (resultSet.next()) {
                User tmpUser = new User();
                tmpUser.setId(resultSet.getLong(1));
                tmpUser.setName(resultSet.getString(2));
                tmpUser.setLastName(resultSet.getString(3));
                tmpUser.setAge(resultSet.getByte(4));
                userList.add(tmpUser);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка получения списка пользователей");
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE user");
        } catch (SQLException e) {
            System.err.println("Ошибка очистки таблицы \"user\"");
            e.printStackTrace();
        }
    }
}
