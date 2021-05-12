package client_server.database;

import client_server.database.singleton.DatabaseConnection;
import client_server.database.singleton.DatabaseStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseRunner {

    private static DatabaseRunner databaseRunner = null;

    private static Connection connection = null;

    private DatabaseRunner() { }

    public static DatabaseRunner getInstance() {
        if (databaseRunner == null) {
            databaseRunner = new DatabaseRunner();
        }

        return databaseRunner;
    }

    private boolean init() {
        connection = DatabaseConnection.getInstance().getConnection();

        if (connection == null) {
            DatabaseConnection.getInstance().showError();
            return false;
        }

        return true;
    }

    public boolean runSql(String sql) {
        if (!init()) {
            return false;
        }

        try {
            DatabaseStatement.getInstance().createStatement(connection).executeUpdate(sql);
        } catch (SQLException throwables) {
//            throwables.printStackTrace();
            return false;
        }

        return true;
    }

    public ResultSet getSqlResult(String sql) {
        if (!init()) {
            return null;
        }

        ResultSet resultSet = null;

        try {
            resultSet = DatabaseStatement.getInstance().createStatement(connection).executeQuery(sql);
        } catch (SQLException throwables) {
//            throwables.printStackTrace();
        }

        return resultSet;
    }

    public void close() {
        DatabaseConnection.getInstance().close();
    }

}
