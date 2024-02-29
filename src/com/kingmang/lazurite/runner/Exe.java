package com.kingmang.lazurite.runner;


import com.kingmang.lazurite.utils.Handler;
import com.kingmang.lazurite.editors.Editor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


public class Exe {



    public static int check = 0;

    public static void main(String[] args) throws IOException {

        RunnerInfo.Help();
        while (true) {
            System.out.println("\n------------------------------------------------------------\n");
            BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));

            String cmd = sc.readLine();
            if (cmd.contains("--help") || cmd.contains("-h")) {
                RunnerInfo.Help();

            } else if (cmd.contains("--run") || cmd.contains("-r")) {
                String[] obj = cmd.split(" ");
                try {
                    Handler.RUN(obj[1], false);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Correct entry form: -r <file>");
                } catch (FileNotFoundException ex) {
                    System.out.println("File " + obj[1] + " not found");
                }

            } else if (cmd.contains("--editor") || cmd.contains("-e")) {
                Editor.openEditor();
            } else if (cmd.contains("--version") || cmd.contains("-v")) {
                System.out.println("---------------------------------");
                System.out.println("Lazurite version: " + RunnerInfo.VERSION());
                System.out.println("---------------------------------");
            } else if (cmd.contains("cls")) {
                System.out.print("\033[H\033[2J");
                System.out.flush();

            } else {
                System.out.println("Command not found!");
            }
        }
    }
}





