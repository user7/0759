/*
 * task_7.1 - реализовать многопоточность в прокси чеккере первым и вторым способом
 * task_7.2 - рабочие ip адреса необходимо складывать в файл good.txt
 *  */

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class Main {
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";

    private static PrintStream goodLogger;

    public static void main(String[] args) {
        int threadType = 1; // выбор реализации мультитрединга
        try {
            goodLogger = new PrintStream(new FileOutputStream("/dev/shm/good.txt"));
            Scanner scan = new Scanner(new File("/dev/shm/ip.txt"));
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                try {
                    String tmp[] = line.split("([ \t]+|:)");
                    String host = tmp[0];
                    int port = 3128;
                    if (tmp.length > 1)
                        port = Integer.parseInt(tmp[1]);
                    InetSocketAddress addr = new InetSocketAddress(host, port);
                    switch (threadType) {
                        case 1:
                            startThread1(addr);
                            break;
                        case 2:
                            startThread2(addr);
                            break;
                        default:
                            startThread3(addr);
                            break;
                    }
                } catch (Exception e) {
                    System.out.println("Bad proxy string '" + line + "': " + e);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
    }

    public static void checkProxy(InetSocketAddress addr) {
        try {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);
            URLConnection conn = new URL("https://vozhzhaev.ru/test.php").openConnection(proxy);
            conn.setConnectTimeout(5000); // 5 seconds
            conn.connect();
            conn.getInputStream().close();
            logProxy(true, addr);
        } catch (Exception e) {
            logProxy(false, addr);
        }
    }

    public static synchronized void logProxy(boolean good, InetSocketAddress addr) {
        String status = good ? ANSI_GREEN + " работает" + ANSI_RESET
                : ANSI_RED + " не работает" + ANSI_RESET;
        String strAddr = addr.getAddress().getHostAddress() + ":" + addr.getPort();
        System.out.println(strAddr + status);
        if (good)
            goodLogger.println(strAddr);
    }

    public static void startThread1(InetSocketAddress addr) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                checkProxy(addr);
            }
        });
        t.start();
    }

    public static void startThread2(InetSocketAddress addr) {
        CheckerThread ct = new CheckerThread(addr);
        ct.start();
    }

    public static void startThread3(InetSocketAddress addr) {
        Thread t = new Thread(new CheckerRunnable(addr));
        t.start();
    }
}

class CheckerThread extends Thread {
    private InetSocketAddress addr;

    public CheckerThread(InetSocketAddress a) {
        addr = a;
    }

    @Override
    public void run() {
        Main.checkProxy(addr);
    }
}

class CheckerRunnable implements Runnable {
    private InetSocketAddress addr;

    public CheckerRunnable(InetSocketAddress a) {
        addr = a;
    }

    @Override
    public void run() {
        Main.checkProxy(addr);
    }
}
