package client_server.server;

import client_server.Command;
import client_server.MessageStreamer;
import client_server.database.dao.FriendShipDao;
import client_server.database.dao.UserDao;
import client_server.database.models.User;
import client_server.util.Color;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable, MessageStreamer {

    private final Socket socket;
    private final ServerSocket serverSocket;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private final UserDao userDao;
    private final FriendShipDao friendShipDao;

    public ClientHandler(Socket socket, ServerSocket serverSocket) throws IOException {
        this.serverSocket = serverSocket;
        this.socket = socket;
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.outputStream.flush();
        this.inputStream = new ObjectInputStream(socket.getInputStream());
        this.userDao =  new UserDao();
        this.friendShipDao = new FriendShipDao();

        startHandler();
    }

    private void startHandler() {
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        printMessage("Client connecting with server");

        try {

            while (true) {
                String command = (String) receive();

                if (command.equals(Command.STOP)) {
                    send("Server stopped");
                    serverSocket.close();
                    break;
                } else if (command.equals(Command.CLIENT_DISCONNECT)) {
                    printMessage("Client disconnected");
                    break;
                } else if (command.equals(Command.REGISTER)) {
                    register();
                } else if (command.equals(Command.LOGIN)) {
                    login();
                } else if (command.equals(Command.ADD_FRIENDS)) {
                    addFriends();
                } else if (command.equals(Command.SHOW_FRIENDS)) {
                    showFriends();
                }
                else {
                    printMessage("Server received the command " + command);
                }
            }

            closeAll();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showFriends() throws IOException, ClassNotFoundException {
        User user = (User) receive();

        List<User> friendList = userDao.getFriends(user);

        String[] friendUsernames = new String[friendList.size()];

        for (int i = 0; i < friendList.size(); i++) {
            friendUsernames[i] = friendList.get(i).getUsername();
        }

        send(friendUsernames);
    }

    private void addFriends() throws IOException, ClassNotFoundException {
        User user = (User) receive();
        String[] usernames = (String[]) receive();

        userDao.addFriends(user, usernames);

        send("Users added successfully");
    }

    private void login() throws IOException, ClassNotFoundException {
        User user = (User) receive();

        User existingUser = userDao.findByUsername(user.getUsername());

        if (existingUser == null) {
            send(null);
            send("User doesn't exist");
            return;
        }

        if (!existingUser.getPassword().equals(user.getPassword())) {
            send(null);
            send("Wrong password");
            return;
        }

        send(existingUser);
        send("Log in successfully");
    }

    private void register() throws IOException, ClassNotFoundException {
        User newUser = (User) receive();

        String errorMessage = "User successfully created. Now you can login";

        if (userDao.findByUsername(newUser.getUsername()) != null) {
            errorMessage = "Username already taken";
            send(errorMessage);
            return;
        }

        userDao.save(newUser);

        send(errorMessage);

    }

    private void printMessage(Object o) {
        synchronized (System.out) {
            System.out.println(Color.CYAN_BOLD + "[Server]: " + Color.BLUE_BOLD + o + Color.RESET);
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
