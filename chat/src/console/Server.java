package console;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Server {
    private final Set<User> clients = new HashSet<>();

    public static void main(String[] args) {
        try {
            Server s = new Server(args.length > 0 ? args[0] : "127.0.0.1");
        } catch (IOException e) {
            System.out.println("Ошибка: " + e);
            e.printStackTrace();
        }
    }

    public Server(String addr) throws IOException {
        ServerSocket listener = new ServerSocket(8188, 10000, InetAddress.getByName(addr));
        while (true) {
            Socket sock = listener.accept();
            User user = new User(sock, this);
            clients.add(user);
            user.getConnection().send(new Message("Введите имя:"));
        }
    }

    public void handleMessage(User sender, Message msg) {
        if (msg.isDisconnect()) {
            clients.remove(sender);
            if (sender.getName() != null) {
                broadcast(sender, new Message(sender.getName() + " покинул чат", "", listUsers()));
            }
        } else if (sender.getName() == null) {
            sender.setName(msg.getSender());
            broadcast(sender, new Message(sender.getName() + " вошёл в чат", "", listUsers()));
            sender.getConnection().send(new Message("Добро пожаловать, " + sender.getName() + "!", "", listUsers()));
        } else {
            broadcast(sender, new Message(msg.getMessage(), sender.getName()));
        }
    }

    public void broadcast(User sender, Message msg) {
        System.out.println(sender.getName() + " >> " + msg);
        for (User c : clients)
            if (c != sender)
                c.getConnection().send(msg);
    }

    public ArrayList<String> listUsers() {
        ArrayList<String> users = new ArrayList<>();
        for (User c : clients) {
            if (c.getName() != null)
                users.add(c.getName());
        }
        return users;
    }
}