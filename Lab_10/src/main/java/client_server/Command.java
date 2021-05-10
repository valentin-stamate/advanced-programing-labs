package client_server;

public abstract class Command {
    public static final String STOP = "exit";
    public static final String CLIENT_DISCONNECT = "disconnect";
    public static final String REGISTER = "register";
    public static final String LOGIN = "login";
    public static final String ADD_FRIENDS = "add friends";
    public static final String SHOW_FRIENDS = "show friends";
    public static final String SEND_MESSAGE = "send message";
    public static final String READ_MESSAGES = "read messages";
    public static final String SHOW_USERS = "show users";

}