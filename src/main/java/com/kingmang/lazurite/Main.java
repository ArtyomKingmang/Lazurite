package com.kingmang.lazurite;

import com.kingmang.lazurite.core.Stop;
import com.kingmang.lazurite.parser.ast.Statement;
import com.kingmang.lazurite.parser.pars.*;
import com.kingmang.lazurite.parser.pars.Console;
import com.kingmang.lazurite.parser.pars.FunctionAdder;
import com.kingmang.lazurite.runtime.LZR.LZRString;
import com.kingmang.lazurite.runtime.Time;
import com.kingmang.lazurite.runtime.Variables;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

import java.util.Scanner;



public class Main  {

    public static String VERSION(){
        return "2.6";
    }
    public static String link = "";
    public static int check = 0;
   // static Settings setting = new Settings();
    public static Path path1;
    public static void main(String[] args) throws IOException{
        while(true)Start();

    }

    public static void Start() throws IOException {
        System.out.println("\n");
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        String cmd = sc.readLine();
        if (cmd.contains("lazurite") || cmd.contains("lzr")) {

            String[] objs = cmd.split(" ");
           if(objs[1].equals("--help")||objs[1].equals("-h")){
                Help();
                check = 0;
            }else if(objs[1].equals("--version")||objs[1].equals("-v")){
                ver();
                check = 0;
            }
         }else if(cmd.contains("cls")|| cmd.contains("clear")){
            check = 0;
            clear();
        }else if(cmd.contains("--help") || cmd.contains("-h")) {
            Help();
        }
        else if (cmd.contains("LZRRun ")) {
                String path = cmd.contains(".lzr") ?
                        cmd.split(" ")[1] :
                        cmd.split(" ")[1] + ".lzr";
                path1 = Paths.get(path).getParent();

                try {
                    if (!path.contains("\\")) {
                        Variables.set("__cwd_run__", new LZRString(System.getProperty("user.dir")));
                    } else {
                        Variables.set("__cwd_run__", new LZRString(new File(path).getParent()));
                    }
                    File file = new File(path);
                    FileReader fr = new FileReader(file);
                    BufferedReader reader = new BufferedReader(fr);
                    String line = reader.readLine();
                    StringBuilder content = new StringBuilder();
                    while (line != null) {
                        content.append(line).append("\n");
                        line = reader.readLine();
                    }
                    Handler.handle(content.toString(), path, false); // Класс Handler сделан для удобного отлова ошибок
                } catch (FileNotFoundException e) {
                    System.out.println("File is not found!");
                } catch (IOException e) {
                    System.out.println("File read error!");
                }

        }else{
            about();
        }

    }

    public  static void Help(){
        System.out.println(
                "lazurite/ lzr +\n" +
                "\n--run / -r - asks for the path to the file and runs it\n" +
                        "--version / -v - returns the version of Lazurite\n" +
                        "--help / -h - show help commands\n" +
                        "--lint/ -l - lint Mode\n"+
                        "--optimize/ -op - optimize program (5 levels)\n" +
                        "cls - clears the command line\n"
        );

    }
    public static void ver(){
        System.out.println("---------------------------------");
        System.out.println("Lazurite version: " + VERSION());
        System.out.println("---------------------------------");
    }

    public static void about(){
        ver();
        System.out.println("Author: Artyom Kingmang");
        System.out.println("---------------------------------");
        System.out.println("Enter: --help for more information");
        System.out.println("---------------------------------");
    }

    public static void clear(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
        check = 0;
    }


}
