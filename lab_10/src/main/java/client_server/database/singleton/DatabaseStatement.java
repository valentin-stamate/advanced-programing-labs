package client_server.database.singleton;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseStatement {

    public static DatabaseStatement instance = null;
    public static String errorMessage = "";

    private DatabaseStatement() {}

    public static DatabaseStatement getInstance() {
        if (instance == null) {
            instance = new DatabaseStatement();
        }

        return instance;
    }

    public Statement createStatement(Connection databaseConnection) throws SQLException {
        if (databaseConnection == null) {
            return null;
        }

        return databaseConnection.createStatement();
    }

    public void showError() {
        System.out.println(errorMessage);
    }

}
