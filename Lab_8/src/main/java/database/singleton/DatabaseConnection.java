package database.singleton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static DatabaseConnection instance = null;
    private static Connection connection = null;
    private static String errorMessage = "";

    private DatabaseConnection() { }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }

        return instance;
    }

    public Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/imdb", "postgres", "postgres");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        errorMessage = ErrorMessage.DATABASE_CONNECTION_ERROR;
        return connection;
    }

    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void showError() {
        System.out.println(errorMessage);
    }
}
