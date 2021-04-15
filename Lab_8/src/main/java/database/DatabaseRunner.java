package database;

import database.singleton.DatabaseConnection;
import database.singleton.DatabaseStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseRunner {

    private static DatabaseRunner databaseRunner = null;

    private static Connection connection = null;
    private static Statement statement = null;

    private DatabaseRunner() { };

    public static DatabaseRunner getInstance() {
        if (databaseRunner == null) {
            databaseRunner = new DatabaseRunner();
        }

        return databaseRunner;
    }

    private boolean init() {
        connection = DatabaseConnection.getInstance().getConnection();
        statement = DatabaseStatement.getInstance().getStatement(connection);

        if (connection == null) {
            DatabaseConnection.getInstance().showError();
            return false;
        }

        if (statement == null) {
            DatabaseStatement.getInstance().showError();
            return false;
        }

        return true;
    }

    public boolean runSql(final String sql) {
        if (!init()) {
            return false;
        }

        try {
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

        return true;
    }

    public ResultSet getSqlResult(final String sql) {
        if (!init()) {
            return null;
        }

        ResultSet resultSet = null;

        try {
            resultSet = statement.executeQuery(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return resultSet;
    }

    public void close() {
        DatabaseConnection.getInstance().close();
        DatabaseStatement.getInstance().close();
    }

}
