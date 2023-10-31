package com.kingmang.lazurite;


import com.kingmang.lazurite.consoleeditor.editor;
import com.kingmang.lazurite.parser.ast.Statement;
import com.kingmang.lazurite.parser.pars.*;
import com.kingmang.lazurite.parser.pars.FunctionAdder;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.TimeUnit;

import java.util.Scanner;



public class Main  {


    public static String VERSION(){
        return "2.7";
    }
    public static int check = 0;



    public static void main(String[] args) throws IOException{
        System.out.println("\n\t*****************LAZURITE******************\n" +
                "\tLazurite "+ VERSION() +" Author: Kingmang\n" +
                "\t*******************************************");


        //Help();
        //while(true)Start();
        String in = "C:\\Users\\crowb\\OneDrive\\Рабочий стол\\lazr\\test.lzr";
        RUN(in);

    }

    public  static void Help(){
        System.out.println(
                "\n--run / -r - asks for the path to the file and runs it\n" +
                        "--version / -v - returns the version of Lazurite\n" +
                        "--help / -h - show help commands\n" +
                        "--editor / -e - open code editor\n" +
                        "cls - clears the command line\n"
        );

    }
    public static void Start() throws IOException {
        System.out.println("\n\n\n------------------------------------------------------------\n\n");
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        String cmd = sc.readLine();
        if (cmd.contains("--help")||cmd.contains("-h")) {
            Help();
        } else if (cmd.contains("--run")||cmd.contains("-r")) {
            System.out.print("\n\nEnter path to your file: ");
            Scanner scan = new Scanner(System.in);
            String in = scan.nextLine();
            RUN(in);
        } else if (cmd.contains("--editor")||cmd.contains("-e")) {
            editor.openEditor();
        } else if (cmd.contains("--version")||cmd.contains("-v")) {
            System.out.println("---------------------------------");
            System.out.println("Lazurite version: "+ VERSION());
            System.out.println("---------------------------------");
        } else if (cmd.contains("cls")) {
            System.out.print("\033[H\033[2J");
            System.out.flush();

        } else {
            System.out.println("Command not found!");
        }
    }


    /*private static void RUN() throws IOException {
        System.out.print("\n\nEnter path to your file: ");
        Scanner scan = new Scanner(System.in);
        String in = scan.nextLine();
        //String in = "C:\\Users\\crowb\\OneDrive\\Рабочий стол\\lazr\\test.lzr";
        RunProgram(SourceLoader.readSource(in));

    }*/
    private static void RUN(String path) throws IOException{
        RunProgram(SourceLoader.readSource(path));
    }



    public static void RunProgram(String input) throws IOException {

        final List<Token> tokens = Lexer.tokenize(input);
        final Parser parser = new Parser(tokens);
        final Statement parsedProgram = parser.parse();
        if (parser.getParseErrors().hasErrors()) {
            System.out.println(parser.getParseErrors());
            return;
        }
        final Statement program;
        program = parsedProgram;
        program.accept(new FunctionAdder());

        try {

            program.execute();


        } catch (Exception ex) {

            Console.handleException(Thread.currentThread(), ex);
        }



    }

    private static String[] LZRArgs = new String[0];
    public static String[] Args() {
        return LZRArgs;
    }

}

