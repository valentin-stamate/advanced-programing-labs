package client_server;

public abstract class Command {
    public static final String STOP = "exit";
    public static final String INFO_STOP = "This command close the connection with the server and stops it";

    public static final String CLIENT_DISCONNECT = "disconnect";
    public static final String INFO_CLIENT_DISCONNECT = "Disconnects the client";

    public static final String REGISTER = "register";
    public static final String INFO_REGISTER = "Registers a new user with a given username and password";

    public static final String LOGIN = "login";
    public static final String INFO_LOGIN = "Logs in a user";

    public static final String ADD_FRIENDS = "add friends";
    public static final String INFO_ADD_FRIENDS = "Adds more friend by their username";

    public static final String SHOW_FRIENDS = "show friends";
    public static final String INFO_SHOW_FRIENDS = "Shows the friend list";

    public static final String SEND_MESSAGE = "send message";
    public static final String INFO_SEND_MESSAGE = "Sends a message to a given user";

    public static final String READ_MESSAGES = "read messages";
    public static final String INFO_READ_MESSAGES = "Displays all the messages";

    public static final String SHOW_USERS = "show users";
    public static final String INFO_SHOW_USERS = "Show all the existing users";

    public static final String HELP = "help";
    public static final String INFO_HELP = "You are already here, so you know what it does";

}