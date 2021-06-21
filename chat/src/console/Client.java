package console;

import java.io.IOException;
import java.util.Scanner;

public class Client {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";

    private Connection connection = null;
    private String name = null;

    public static void main(String[] args) {
        Client c = new Client(args.length > 0 ? args[0] : "");
    }

    private Client(String addr) {
        try {
            connection = new Connection(addr, msg -> handleMessage(msg));
            Scanner s = new Scanner(System.in);
            while (s.hasNext()) {
                String input = s.nextLine();
                Message msg = new Message();
                if (name == null) {
                    name = input.trim();
                    msg.setSender(name);
                } else {
                    consolePrint(ANSI_GREEN, "[" + name + "] " + input);
                    msg.setMessage(input);
                }
                connection.send(msg);
            }
        } catch (IOException e) {
        }
        if (connection != null)
            connection.close();
    }

    private void handleMessage(Message msg) {
        if (msg == null) {
            consolePrint(ANSI_RED, "Соединение разорвано");
            System.exit(0);
        } else {
            if (msg.hasMessage()) {
                // если нет сообщения
                consolePrint(msg.hasSender() ? ANSI_YELLOW : ANSI_RED, msg.getMessage());
            }
            if (msg.hasUsers()) {
                // обновлен список пользователей
                consolePrint(ANSI_RED, "Сейчас на сервере: " + String.join(", ", msg.getUsers()));
            }
        }
    }

    private static void consolePrint(String color, String message) {
        System.out.println(color + message + ANSI_RESET);
    }
}