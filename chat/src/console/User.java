package console;

import java.io.IOException;
import java.net.Socket;

class User {
    private Connection connection;
    private String name;

    public User(Socket socket, Server server) throws IOException {
        connection = new Connection(socket, msg -> server.handleMessage(this, msg));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Connection getConnection() {
        return connection;
    }
}