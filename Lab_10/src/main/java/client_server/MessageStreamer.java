package client_server;

import java.io.IOException;

public interface MessageStreamer {
    void send(Object o) throws IOException;
    Object receive() throws IOException, ClassNotFoundException;
    void closeAll() throws IOException;
}
