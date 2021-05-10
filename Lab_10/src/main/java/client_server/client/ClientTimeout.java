package client_server.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientTimeout implements Runnable {

    private int timeOut = 120;
    private final Socket clientSocket;
    private final Scanner scanner;
    private final InputStream inputStream;
    private final OutputStream outputStream;

    public ClientTimeout(Socket socket, Scanner scanner, InputStream inputStream, OutputStream outputStream) {
        this.clientSocket = socket;
        this.scanner = scanner;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public void resetTimeOut() {
        timeOut = 120;
    }

    @Override
    public void run() {
        try {
            while (timeOut != 0) {
                Thread.sleep(1000);
                timeOut--;
            }

            inputStream.close();
            outputStream.close();
            clientSocket.close();
            scanner.close();
        } catch (InterruptedException | IOException e) {
//            e.printStackTrace();
        }
        synchronized (System.out) {
            System.out.println("Client closed due to inactivity");
        }
    }
}
