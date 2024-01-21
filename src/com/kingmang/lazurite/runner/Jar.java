package com.kingmang.lazurite.runner;

import com.kingmang.lazurite.utils.Handler;
import com.kingmang.lazurite.editors.Editor;

import java.io.FileNotFoundException;
import java.io.IOException;


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
            String[] obj = cmd.split(" ");
            try {
                Handler.RUN(obj[1]);
            }catch(ArrayIndexOutOfBoundsException e){
                System.out.println("Correct entry form: -r <file>");
            }catch (FileNotFoundException ex){
                System.out.println("File "+obj[1]+ " not found");
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