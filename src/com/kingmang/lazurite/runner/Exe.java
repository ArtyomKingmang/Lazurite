package com.kingmang.lazurite.runner;


import com.kingmang.lazurite.Handler;
import com.kingmang.lazurite.editors.Editor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


public class Exe {

    public static String VERSION() {
        return "2.8";
    }

    public static int check = 0;

    public static void main(String[] args) throws IOException {


            /*Help();
            while(true)Start();*/

            String file = "test.lzr";
            String in = "C:\\Users\\crowb\\Desktop\\Lazurite\\test\\" + file;
            Handler.RUN(in);
    }


        public static void Help () {
            System.out.println("\n\t-------"+VERSION()+"-------\n" +
                    "\tLazurite Console" + "\n" +
                    "\t-----------------");
            System.out.println("------------------------------------------------------------");
            System.out.println(
                    "--run / -r - asks for the path to the file and runs it\n" +
                            "--version / -v - returns the version of Lazurite\n" +
                            "--help / -h - show help commands\n" +
                            "--editor / -e - open code editor\n" +
                            "cls - clears the command line\n"
            );

        }
        public static void Start() throws IOException {
            System.out.println("\n------------------------------------------------------------\n");
            BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
            String cmd = sc.readLine();
            if (cmd.contains("--help") || cmd.contains("-h")) {
                Help();

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
                System.out.println("Lazurite version: " + VERSION());
                System.out.println("---------------------------------");
            } else if (cmd.contains("cls")) {
                System.out.print("\033[H\033[2J");
                System.out.flush();

            } else {
                System.out.println("Command not found!");
            }
        }


    }



