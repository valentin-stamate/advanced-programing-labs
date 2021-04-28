package client_server.server;

import client_server.Command;
import client_server.MessageStreamer;
import client_server.util.Color;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientHandler implements Runnable, MessageStreamer {

    private final Socket socket;
    private final ServerSocket serverSocket;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;

    public ClientHandler(Socket socket, ServerSocket serverSocket) throws IOException {
        this.serverSocket = serverSocket;
        this.socket = socket;
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.outputStream.flush();
        this.inputStream = new ObjectInputStream(socket.getInputStream());

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
                } else {
                    printMessage("Server received the command " + command);
                }
            }

            closeAll();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
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
