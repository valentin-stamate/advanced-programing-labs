package client_server.database.models;

import java.sql.Timestamp;
import java.util.Date;

public class Message {
    private int id;
    private int user;
    private int userFrom;
    private String message;
    private Timestamp timestamp;

    public Message(int user, int userFrom, String message) {
        this.user = user;
        this.userFrom = userFrom;
        this.message = message;
        this.timestamp = new Timestamp((new Date()).getTime());;
    }

    public Message(int user, int userFrom, String message, Timestamp timestamp) {
        this.user = user;
        this.userFrom = userFrom;
        this.message = message;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public int getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(int userFrom) {
        this.userFrom = userFrom;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", user=" + user +
                ", userFrom=" + userFrom +
                ", message='" + message + '\'' +
                '}';
    }
}
