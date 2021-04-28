package client_server.client;

import client_server.MessageStreamer;
import client_server.Command;
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

                String command = readCommand();

                send(command);

                if (command.equals(Command.STOP)) {
                    String message = (String) receive();
                    printMessage(message);
                    break;
                } if (command.equals(Command.CLIENT_DISCONNECT)) {
                    break;
                }

            }

            closeAll();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String readCommand() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private void printMessage(Object o) {
        synchronized (System.out) {
            System.out.println(Color.CYAN_BOLD + "[Client]: " + Color.BLUE_BOLD + "" + o + Color.RESET);
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
