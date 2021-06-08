import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) {
        try {
            if (args.length == 2 && args[0].equals("-s")) {
                Server s = new Server(args[1]);
                s.run();
            } else if (args.length == 2 && args[0].equals("-c") {
                Client c = new Client(args[1]);
                c.run();
            } else {
                System.out.println("Некорректные параметры командной строки,");
                System.out.println("для запуска сервера: chat -s address:port");
                System.out.println("для запуска клиента: chat -c address:port");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static int parsePort(String addr) {
        String[] a = addr.split(":");
        int port = 9090;
        if (a.length > 1)
            port = Integer.parseInt(a[1]);
        return port;
    }

    public static InetAddress parseHost(String addr) throws UnknownHostException {
        return InetAddress.getByName(addr.split(":")[0]);
    }
}

class Server {
    ServerSocket sock;

    public Server(String addr) throws IOException {
        sock = new ServerSocket(Main.parsePort(addr), 100, Main.parseHost(addr));
    }

    public void run() {

    }
}

class Client {
    public Client(String client) {

    }

    public void run() {
    }
}