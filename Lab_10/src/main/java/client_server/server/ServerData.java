package client_server.server;

public class ServerData {
    private int numberOfConnections = 0;

    public int getNumberOfConnections() {
        return numberOfConnections;
    }

    public void increaseConnections() {
        numberOfConnections++;
    }

    public void decreaseConnections() {
        numberOfConnections--;
    }
}
