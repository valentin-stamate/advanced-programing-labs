package client_server.database.singleton;

public abstract class ErrorMessage {
    public static final String DATABASE_CONNECTION_ERROR = "Error connecting with database";
    public static final String DATABASE_CONNECTION_CLOSE_ERROR = "Error closing the database";
    public static final String DATABASE_STATEMENT_ERROR = "Error getting the statement instance";
    public static final String DATABASE_STATEMENT_CLOSE_ERROR = "Error closing statement";
}
