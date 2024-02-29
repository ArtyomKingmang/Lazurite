package com.kingmang.lazurite.runner;

import com.kingmang.lazurite.utils.Handler;
import com.kingmang.lazurite.editors.Editor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


public class Jar {


    public static void main(String[] args) throws IOException {
        /* Concat args in one string */
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            strBuilder.append(args[i]);
        }
        String cmd = strBuilder.toString(); // program args

        if (cmd.contains("--help") || cmd.contains("-h")) {
            RunnerInfo.Help();
        } else if (cmd.contains("--run") || cmd.contains("-r")) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter file name: ");
            String program = scanner.nextLine();
            try {
                Handler.RUN(program,false);
            }catch (FileNotFoundException e){
                System.out.println("file not found");
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