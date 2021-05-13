package com.perosal.lab_11.legacy.database.models;

import java.io.Serializable;

public class Message implements Serializable {

    public static final String TABLE_NAME = "messages";
    public static final String ID_COLUMN_NAME = "id";
    public static final String MESSAGE_COLUMN_NAME = "message";
    public static final String FROM_COLUMN_NAME = "person_model_id";

    private long id;
    private String message;
    private long userFrom;

    public Message(long userFrom, String message) {
        this.userFrom = userFrom;
        this.message = message;
    }

    public Message() { }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserFrom(int userFrom) {
        this.userFrom = userFrom;
    }

    public long getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(long userFrom) {
        this.userFrom = userFrom;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", userFrom=" + userFrom +
                ", message='" + message + '\'' +
                '}';
    }
}
