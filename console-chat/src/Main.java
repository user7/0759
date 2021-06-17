import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static java.lang.System.out;
import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) {
        try {
            if (args.length == 2 && args[0].equals("-s")) {
                Server s = new Server(parseAddr(args[1]));
                s.run();
            } else if (args.length == 2 && args[0].equals("-c")) {
                Client c = new Client(parseAddr(args[1]));
                c.run();
            } else {
                out.println("Некорректные параметры командной строки");
                out.println("для запуска сервера: chat -s адрес:порт");
                out.println("для запуска клиента: chat -c адрес:порт");
            }
        } catch (Exception e) {
            out.println("Ошибка: " + e);
            e.printStackTrace();
        }
    }

    private static InetSocketAddress parseAddr(String addr) {
        String[] a = addr.split(":");
        int port = 9090;
        if (a.length > 1)
            port = parseInt(a[1]);
        return new InetSocketAddress(a[0], port);
    }
}

class ConnectedClient implements Runnable {
    private final Server srv;
    private final Socket sock;
    private String name;
    private DataOutputStream sockOut;

    public ConnectedClient(Server srv, Socket sock) {
        this.srv = srv;
        this.sock = sock;
    }

    public String getNameSafe() {
        return name == null ? sock.toString() : name;
    }

    public String getName() {
        return name;
    }

    public void send(String msg) {
        try {
            sockOut.writeUTF(msg);
        } catch (Exception e) {
            out.println("не могу послать сообщение " + getNameSafe() + ": " + e);
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            sockOut = new DataOutputStream(sock.getOutputStream());
            send("!Введите имя:");
            DataInputStream in = new DataInputStream(sock.getInputStream());
            while (!sock.isInputShutdown()) {
                String line = in.readUTF();
                if (name == null) {
                    name = line;
                    out.println("принято имя клиента: " + getNameSafe());
                    send("!Сейчас в чате: " + srv.listClients());
                    srv.broadcast_notice(this, "Подключился " + getNameSafe());
                } else {
                    out.println("сообщение от клиента " + getNameSafe() + ": " + line);
                    if (!line.isEmpty())
                        srv.broadcast_message(this, line);
                }
            }
            out.println("Клиент " + getNameSafe() + " отключился");
        } catch (Exception e) {
            out.println("Клиент " + getNameSafe() + " отключился с ошибкой: " + e);
            e.printStackTrace();
        }
        srv.disconnect(this);
    }
}

class Server {
    private final InetSocketAddress addr;
    private final Set<ConnectedClient> clients = new HashSet<>();

    public Server(InetSocketAddress addr) {
        this.addr = addr;
    }

    public void run() throws IOException {
        ServerSocket listener = new ServerSocket(addr.getPort(), 10000, addr.getAddress());
        while (true) {
            Socket cs = listener.accept();
            ConnectedClient c = new ConnectedClient(this, cs);
            clients.add(c);
            Thread ct = new Thread(c);
            ct.start();
        }
    }

    public void broadcast(ConnectedClient sender, String msg, String prefix) {
        synchronized (clients) {
            for (ConnectedClient c : clients)
                if (c != sender)
                    c.send(prefix + msg);
        }
    }

    public void broadcast_notice(ConnectedClient sender, String msg) {
        broadcast(sender, msg, "!");
    }

    public void broadcast_message(ConnectedClient sender, String msg) {
        broadcast(sender, msg, " " + "[" + sender.getNameSafe() + "] ");
    }


    public void disconnect(ConnectedClient client) {
        broadcast_notice(client, client.getNameSafe() + " вышел из чата");
        synchronized (clients) {
            clients.remove(client);
        }
    }

    public String listClients() {
        synchronized (clients) {
            StringBuilder str = new StringBuilder();
            for (ConnectedClient c : clients) {
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
}

class Client {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";

    private final InetSocketAddress addr;

    public Client(InetSocketAddress addr) {
        this.addr = addr;
    }

    public void run() throws IOException {
        Socket sock = new Socket(addr.getAddress(), addr.getPort());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DataInputStream r = new DataInputStream(sock.getInputStream());
                    String color;
                    while (!sock.isInputShutdown()) {
                        String line = r.readUTF();
                        if (line.startsWith("!"))
                            color = ANSI_RED;
                        else
                            color = ANSI_YELLOW;
                        out.println(color + line.substring(1) + ANSI_RESET);
                    }
                } catch (Exception e) {
                    out.println("ошибка чтения: " + e);
                    e.printStackTrace();
                }
            }
        }).start(); // принимающий тред
        DataOutputStream outputStream = new DataOutputStream(sock.getOutputStream());
        Scanner s = new Scanner(System.in);
        String name = null;
        while (!sock.isOutputShutdown()) {
            String line = s.nextLine();
            outputStream.writeUTF(line);
            if (name != null)
                System.out.println(ANSI_GREEN + "[" + name + "] " + line + ANSI_RESET);
            else
                name = line;
        }
    }
}