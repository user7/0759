package com.company;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            testEncReading(argN(args, 0, "/dev/shm/enc.txt"), argN(args, 1, null));
        } catch (Exception e) {
            System.out.println("sorry: " + e);
        }
    }

    public static String argN(String[] args, int i, String def) {
        return args.length > i ? args[i] : def;
    }

    public static void testEncReading(String fname, String encoding) throws IOException {
        FileInputStream in = new FileInputStream(fname);
        int i;
        StringBuilder text = new StringBuilder();
        while ((i = in.read()) != -1) {
            if (i != 13 && i != 10)
                text.append((char) i);
        }
        report("-byte reader-", text.toString());

        StringBuilder text2 = new StringBuilder();
        Scanner s;
        if (encoding == null)
            s = new Scanner(new File(fname)); // сканер сам угадывает кодировку
        else
            s = new Scanner(new File(fname), encoding);
        while (s.hasNextLine()) {
            text2.append(s.nextLine());
        }
        report("-scanner reader-", text2.toString());
    }

    public static void report(String msg, String t) {
        System.out.println();
        System.out.println(msg);
        System.out.println("charactes: " + t.length());
        System.out.println("dashed: ");
        for (int j = 0; j < t.length(); j++) {
            System.out.print(t.charAt(j));
            if (j != t.length() - 1)
                System.out.print("-");
        }
        System.out.println();
        String splits[] = t.split("(?<=.)");
        System.out.println("split & dashed: " + String.join("-", splits));
    }
}