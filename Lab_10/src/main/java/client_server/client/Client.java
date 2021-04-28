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
import java.util.Scanner;

public class Client implements Runnable, MessageStreamer {

    private final Socket socket;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private User userInstance = null;

    public Client() throws IOException {
        this.socket = new Socket(Server.DOMAIN_NAME, Server.PORT);
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.outputStream.flush();
        this.inputStream = new ObjectInputStream(socket.getInputStream());

        startClient();
    }

    public void startClient() {
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try {

            while (true) {

                String command = readLine();

                send(command);

                if (command.equals(Command.STOP)) {
                    String message = (String) receive();
                    printMessage(message);
                    break;
                } if (command.equals(Command.CLIENT_DISCONNECT)) {
                    break;
                } if (command.equals(Command.REGISTER)) {
                    register();
                } if (command.equals(Command.LOGIN)) {
                    login();
                }

            }

            closeAll();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
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
        Scanner scanner = new Scanner(System.in);
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
