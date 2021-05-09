package client_server.client;

import java.sql.Timestamp;

public class MessageRepresentation {
    private final String userFrom;
    private final String message;
    private final Timestamp timestamp;

    public MessageRepresentation(String userFrom, String message, Timestamp timestamp) {
        this.userFrom = userFrom;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getUserFrom() {
        return userFrom;
    }

    public String getMessage() {
        return message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
