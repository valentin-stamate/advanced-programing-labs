package com.perosal.lab_11.legacy;

public class MessageRepresentation {
    private final String userFrom;
    private final String message;

    public MessageRepresentation(String userFrom, String message) {
        this.userFrom = userFrom;
        this.message = message;
    }

    public String getUserFrom() {
        return userFrom;
    }

    public String getMessage() {
        return message;
    }
}
