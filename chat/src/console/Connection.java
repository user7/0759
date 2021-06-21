package console;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;


public class Connection {
    private Socket sock;
    private ObjectOutputStream sockOut;
    private Thread readerThread;
    private Consumer<Message> callback;

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

    public Connection(String addr, Consumer<Message> callback) throws IOException {
        this.sock = new Socket(addr.isEmpty() ? "127.0.0.1" : addr, 8188);
        this.callback = callback;
        init();
    }

    public Connection(Socket socket, Consumer<Message> callback) throws IOException {
        this.sock = socket;
        this.callback = callback;
        init();
    }

    public void init() throws IOException {
        sockOut = new ObjectOutputStream(sock.getOutputStream());
        readerThread = new Thread(() -> {
            try {
                ObjectInputStream r = new ObjectInputStream(sock.getInputStream());
                while (true) {
                    Message m = (Message) r.readObject();
                    callback.accept(m);
                }
            } catch (Exception e) {
                System.out.println("Ошибка при получении: " + e);
            }
            callback.accept(new Message()); // сигнал отключения
        });
        readerThread.start();
    }

    public void send(Message message) {
        try {
            sockOut.writeObject(message);
        } catch (IOException e) {
            System.out.println("Ошибка при отправке: " + e);
            callback.accept(new Message()); // сигнал отключения
        }
    }
}