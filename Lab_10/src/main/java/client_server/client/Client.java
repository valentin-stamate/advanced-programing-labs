package client_server.client;

import client_server.MessageStreamer;
import client_server.Command;
import client_server.database.models.User;
import client_server.server.Server;
import client_server.util.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client implements Runnable, MessageStreamer {

    private final Socket socket;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private User userInstance = null;
    private final Scanner scanner;
    private final ClientTimeout clientTimeout;

    public Client() throws IOException {
        this.socket = new Socket(Server.DOMAIN_NAME, Server.PORT);
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.outputStream.flush();
        this.inputStream = new ObjectInputStream(socket.getInputStream());
        this.scanner = new Scanner(System.in);
        this.clientTimeout = new ClientTimeout(socket, scanner, inputStream, outputStream);

        startClient();
    }

    public void startClient() {
        Thread thread = new Thread(this);
        thread.start();

        thread = new Thread(clientTimeout);
        thread.start();
    }

    @Override
    public void run() {
        try {

            while (true) {

                printMessage("");

                String command = readLine();

                clientTimeout.resetTimeOut();

                if (command.equals(Command.HELP)) {
                    showHelp();
                    continue;
                }

                send(command);

                if (command.equals(Command.STOP)) {
                    String message = (String) receive();
                    printMessage(message);
                    break;
                } else if (command.equals(Command.CLIENT_DISCONNECT)) {
                    break;
                } else if (command.equals(Command.REGISTER)) {
                    register();
                } else if (command.equals(Command.LOGIN)) {
                    login();
                } else if (command.equals(Command.SHOW_USERS)) {
                    showUsers();
                }
                else if (command.equals(Command.ADD_FRIENDS)) {
                    if (userIsLogged()) {
                        addFriends();
                        continue;
                    }
                    printMessage("Log in first");
                } else if (command.equals(Command.SHOW_FRIENDS)) {
                    if (userIsLogged()) {
                        showFriends();
                        continue;
                    }
                    printMessage("Log in first");
                } else if (command.equals(Command.SEND_MESSAGE)) {
                    if (userIsLogged()) {
                        sendMessage();
                        continue;
                    }
                    printMessage("Log in first");
                } else if (command.equals(Command.READ_MESSAGES)) {
                    if (userIsLogged()) {
                        readMessages();
                        continue;
                    }
                    printMessage("Log in first");
                }

            }

            closeAll();
        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
        }
    }

    private void showHelp() {
        printMessage("You can use the following commands:");

        System.out.printf("%-20s %s\n", Command.REGISTER, Command.INFO_REGISTER);
        System.out.printf("%-20s %s\n", Command.LOGIN, Command.INFO_LOGIN);
        System.out.printf("%-20s %s\n", Command.ADD_FRIENDS, Command.INFO_ADD_FRIENDS);
        System.out.printf("%-20s %s\n", Command.SEND_MESSAGE, Command.INFO_SEND_MESSAGE);
        System.out.printf("%-20s %s\n", Command.READ_MESSAGES, Command.INFO_READ_MESSAGES);
        System.out.printf("%-20s %s\n", Command.SHOW_USERS, Command.INFO_SHOW_USERS);
        System.out.printf("%-20s %s\n", Command.CLIENT_DISCONNECT, Command.INFO_CLIENT_DISCONNECT);
        System.out.printf("%-20s %s\n", Command.STOP, Command.INFO_STOP);
        System.out.printf("%-20s %s\n", Command.HELP, Command.INFO_HELP);

    }

    private void showUsers() throws IOException, ClassNotFoundException {
        String[] users = (String[]) receive();

        printMessage("Existing users:");

        for (String user : users) {
            System.out.println(user);
        }
    }

    private void readMessages() throws IOException, ClassNotFoundException {
        printMessage("Your message list:");
        send(userInstance);

        String[] messages = (String[]) receive();

        for (String message : messages) {
            System.out.println(message);
        }
    }

    private void sendMessage() throws IOException {
        printMessage("Send message to:");
        String userTo = readLine();
        printMessage("Message:");
        String message = readLine();

        send(userInstance.getUsername());
        send(userTo);
        send(message);
    }

    private void showFriends() throws IOException, ClassNotFoundException {
        printMessage("Below is your friend list");
        send(userInstance);

        String[] friendList = (String[]) receive();

        StringBuilder stringBuilder = new StringBuilder();

        for (String username : friendList) {
            stringBuilder.append(" -> ").append(username).append("\n");
        }

        printMessage(stringBuilder.toString());
    }

    private void addFriends() throws IOException, ClassNotFoundException {
        printMessage("Enter the usernames of you desired friends. To stop the process enter 'done'");
        List<String> usernames = new ArrayList<>();

        while (true) {
            String username = readLine();

            if (username.equals("done")) {
                break;
            }

            usernames.add(username);
        }

        send(userInstance);
        String[] usernamesList = usernames.toArray(new String[0]);
        send(usernamesList);

        String errorMessage = (String) receive();
        printMessage(errorMessage);
    }

    private boolean userIsLogged() {
        return userInstance != null;
    }

    private void login() throws IOException, ClassNotFoundException {
        printMessage("Enter your username");
        String username = readLine();

        printMessage("Enter your password");
        String password = readLine();

        send(new User(username, password));

        userInstance = (User) receive();
        String errorMessage = (String) receive();

        if (!errorMessage.equals("")) {
            printMessage(errorMessage);
        }
    }

    private void register() throws IOException, ClassNotFoundException {
        printMessage("Enter your username");
        String username = readLine();

        printMessage("Enter your password");
        String password = readLine();

        User newUser = new User(username, password);

        send(newUser);

        String message = (String) receive();
        printMessage(message);
    }

    private String readLine() {
        return scanner.nextLine();
    }

    private void printMessage(Object o) {
        synchronized (System.out) {
            String logMessage = "Client";

            if (userInstance != null) {
                logMessage = userInstance.getUsername();
            }

            System.out.println(Color.CYAN_BOLD + "[" + logMessage + "]: " + Color.BLUE_BOLD + "" + o + Color.RESET);
        }
    }

    @Override
    public void send(Object o) throws IOException {
        outputStream.writeObject(o);
        outputStream.flush();
    }

    @Override
    public Object receive() throws IOException, ClassNotFoundException {
        return inputStream.readObject();
    }

    @Override
    public void closeAll() throws IOException {
        inputStream.close();
        outputStream.close();
        socket.close();
    }
}
