package com.kingmang.lazurite.consoleeditor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class editor {


    public static void openEditor() {
        Scanner scanner = new Scanner(System.in);
        int lineNumber = 1;
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("============================================================");
        System.out.println("                 Lazurite code editor 0.1");
        System.out.println("============================================================");
        System.out.println("           -close   - close editor");
        System.out.println("           -save    - save the code to the file INDEX.lzr ");
        System.out.println("============================================================");
        StringBuilder code = new StringBuilder();

        while (true) {
            System.out.print(lineNumber + " ~ ");
            String line = scanner.nextLine();

            if ("-close".equals(line)) {
                try {
                    FileWriter writer = new FileWriter("INDEX.lzr");
                    writer.write(code.toString());
                    writer.close();
                    System.out.println("Код сохранен в файл INDEX.lzr");
                } catch (IOException e) {
                    System.out.println("IOException");
                }
                System.out.print("\033[H\033[2J");
                System.out.flush();
                break;

            } else if ("-save".equals(line)) {
                try {
                    FileWriter writer = new FileWriter("INDEX.lzr");
                    writer.write(code.toString());
                    writer.close();
                    System.out.println("Код сохранен в файл INDEX.lzr");
                } catch (IOException e) {
                    System.out.println("IOException");
                }
            }else {
                code.append(line).append("\n");
                lineNumber++;
            }
        }

    }
}