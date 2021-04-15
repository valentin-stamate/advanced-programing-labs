package database.singleton;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseStatement {

    public static DatabaseStatement instance = null;
    public static Statement statement = null;
    public static String errorMessage = "";

    private DatabaseStatement() {}

    public static DatabaseStatement getInstance() {
        if (instance == null) {
            instance = new DatabaseStatement();
        }

        return instance;
    }

    public Statement getStatement(Connection databaseConnection) {
        if (databaseConnection == null) {
            return null;
        }

        if (statement == null) {
            try {
                statement = databaseConnection.createStatement();
            } catch (SQLException throwables) {
                errorMessage = ErrorMessage.DATABASE_STATEMENT_ERROR;
                throwables.printStackTrace();
            }
        }
        return statement;
    }

    public void showError() {
        System.out.println(errorMessage);
    }

    public void close() {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
