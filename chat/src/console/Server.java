package console;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
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
            user.getConnection().send("Введите имя:");
        }
    }

    public void handleMessage(User sender, String msg) {
        if (msg == null) {
            if (sender.getName() != null)
                broadcast(sender, sender.getName() + " покинул чат");
            clients.remove(sender);
        } else if (sender.getName() == null) {
            sender.setName(msg.trim());
            broadcast(sender, sender.getName() + " вошёл в чат");
            sender.getConnection().send("Добро пожаловать, сейчас в чате: " + listUsers());
        } else {
            broadcast(sender, "[" + sender.getName() + "] " + msg);
        }
    }

    public void broadcast(User sender, String msg) {
        System.out.println(sender.getName() + " >> " + msg);
        for (User c : clients)
            if (c != sender)
                c.getConnection().send(msg);
    }

    public String listUsers() {
        StringBuilder str = new StringBuilder();
        for (User c : clients) {
            String name = c.getName();
            if (name != null) {
                if (str.length() != 0)
                    str.append(", ");
                str.append(name);
            }
        }
        return str.toString();
    }
}