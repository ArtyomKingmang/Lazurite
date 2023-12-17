package com.kingmang.lazurite;


import com.kingmang.lazurite.editors.editor;
import com.kingmang.lazurite.parser.pars.*;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;


public class Main {


    public static String VERSION() {
        return "2.7";
    }

    public static int check = 0;


    public static void main(String[] args) throws IOException {

        System.out.println("\n\t*****************LAZURITE******************\n" +
                "\tLazurite " + VERSION() + "\n" +
                "\t*******************************************");

            /*Help();
            while(true)Start();*/
            //Scanner scan = new Scanner(System.in);
            String file = "test.lzr";
            String in = "C:\\Users\\crowb\\OneDrive\\Рабочий стол\\lzr\\Lazurite\\test\\" + file;
            RUN(in);
    }


        public static void Help () {
            System.out.println(
                    "\n--run / -r - asks for the path to the file and runs it\n" +
                            "--version / -v - returns the version of Lazurite\n" +
                            "--help / -h - show help commands\n" +
                            "--editor / -e - open code editor\n" +
                            "cls - clears the command line\n"
            );

        }
        public static void Start() throws IOException {
            String input = null;
            System.out.println("\n\n\n------------------------------------------------------------\n\n");
            BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
            String cmd = sc.readLine();
            if (cmd.contains("--help") || cmd.contains("-h")) {
                Help();

            } else if (cmd.contains("--run") || cmd.contains("-r")) {
                System.out.print("\n\nEnter path to your file: ");
                Scanner scan = new Scanner(System.in);
                String in = scan.nextLine();
                RUN(in);

            } else if (cmd.contains("--editor") || cmd.contains("-e")) {
                editor.openEditor();
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

        public static void RUN (String path) throws IOException {
            Handler.RunProgram(SourceLoader.readSource(path));
        }
    }



