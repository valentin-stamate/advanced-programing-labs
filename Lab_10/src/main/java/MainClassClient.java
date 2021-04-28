import client_server.client.Client;
import java.io.IOException;

public class MainClassClient {

    public static void main(String[] args) {
        try {
            new Client();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
