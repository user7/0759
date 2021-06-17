package console;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.function.Consumer;

public class Connection {
    private Socket sock;
    private DataOutputStream sockOut;
    private Thread readerThread;
    private Consumer<String> callback;

    public boolean isClosed() {
        return sock == null || sock.isClosed();
    }

    public void close() {
        if (sock == null) // dummy подключение
            return;
        try {
            sock.close();
        } catch (IOException e) {
            System.out.println("Ошибка при отключении: " + e);
        }
        if (readerThread != null)
            readerThread.interrupt();
    }

    public Connection() {
    } // создать dummy подключение

    public Connection(String addr, Consumer<String> callback) throws IOException {
        this.sock = new Socket(addr.isEmpty() ? "127.0.0.1" : addr, 8188);
        this.callback = callback;
        init();
    }

    public Connection(Socket socket, Consumer<String> callback) throws IOException {
        this.sock = socket;
        this.callback = callback;
        init();
    }

    public void init() throws IOException {
        sockOut = new DataOutputStream(sock.getOutputStream());
        readerThread = new Thread(() -> {
            try {
                DataInputStream r = new DataInputStream(sock.getInputStream());
                while (true) {
                    callback.accept(r.readUTF());
                }
            } catch (Exception e) {
                System.out.println("Ошибка при получении: " + e);
            }
            callback.accept(null); // сигнал отключения
        });
        readerThread.start();
    }

    public void send(String message) {
        try {
            sockOut.writeUTF(message);
        } catch (IOException e) {
            System.out.println("Ошибка при отправке: " + e);
            callback.accept(null); // сигнал отключения
        }
    }
}