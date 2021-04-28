package client_server.server;

import client_server.util.Color;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server implements Runnable {

    public static String DOMAIN_NAME = "localhost";
    public static int PORT = 80;

    public void startServer() {
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        System.out.printf(Color.GREEN_BOLD + "The server is running on domain " + Color.YELLOW + "%s" + Color.GREEN + " listening to port " + Color.YELLOW + "%d" + Color.RESET + "%n\n", DOMAIN_NAME, PORT);
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();

                new ClientHandler(clientSocket, serverSocket);
            }

        } catch (SocketException e) {
            System.out.println("\n" + Color.RED_BOLD + "Server stopped" + Color.RESET);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
