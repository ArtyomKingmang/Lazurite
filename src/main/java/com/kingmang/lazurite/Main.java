package com.kingmang.lazurite;

import com.kingmang.lazurite.lib._StopExeption;
import com.kingmang.lazurite.parser.ast.Statement;
import com.kingmang.lazurite.parser.pars.*;
import com.kingmang.lazurite.parser.pars.FunctionAdder;
import com.kingmang.lazurite.runtime.TimeMeasurement;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.TimeUnit;

import java.util.Scanner;



public class Main  {

    public static String VERSION(){
        return "2.5";
    }
    public static int check = 0;


    public static void main(String[] args) throws IOException{
        System.out.println("\n\t*****************LAZURITE******************\n" +
                "\tLazurite "+ VERSION() +" Author: Kingmang\n" +
                "\t*******************************************");


        Help();
        while(true)Start();



    }

    public  static void Help(){
        System.out.println(
                "\n--run / -r - asks for the path to the file and runs it\n" +
                        "--version / -v - returns the version of Lazurite\n" +
                        "--help / -h - show help commands\n" +
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
            RUN();
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


    public static void RUN() throws IOException {
        System.out.print("\n\nEnter path to your file: ");
        Scanner scan = new Scanner(System.in);
        String in = scan.nextLine();
        final RunOptions options = new RunOptions();
        RunProgram(SourceLoader.readSource(in), options);

    }



    public static void RunProgram(String input, RunOptions options) throws IOException {


        options.validate();
        final TimeMeasurement measurement = new TimeMeasurement();
        measurement.start("Tokenize time");
        final List<Token> tokens = Lexer.tokenize(input);
        measurement.stop("Tokenize time");
        if (options.showTokens) {
            final int tokensCount = tokens.size();
            for (int i = 0; i < tokensCount; i++) {
                System.out.println(i + " " + tokens.get(i));
            }
        }

        measurement.start("Parse time");
        final Parser parser = new Parser(tokens);
        final Statement parsedProgram = parser.parse();
        measurement.stop("Parse time");
        if (options.showAst) {
            System.out.println(parsedProgram.toString());
        }
        if (parser.getParseErrors().hasErrors()) {
            System.out.println(parser.getParseErrors());
            return;
        }
        if (options.lintMode) {
            Linter.lint(parsedProgram);
            return;

        }
        final Statement program;
        if (options.optimizationLevel > 0) {
            measurement.start("Optimization time");
            program = Optimizer.optimize(parsedProgram, options.optimizationLevel, options.showAst);
            measurement.stop("Optimization time");
            System.out.println(program.toString());
            if (options.showAst) {
                System.out.println(program.toString());
            }
        } else {
            program = parsedProgram;
        }
        program.accept(new FunctionAdder());

        try {
            measurement.start("Execution time");
            program.execute();

        } catch (_StopExeption ex) {
            // skip

        } catch (Exception ex) {

            Console.handleException(Thread.currentThread(), ex);
        }
        finally {
            if (options.showMeasurements) {
                measurement.stop("Execution time");
                System.out.println("======================");
                System.out.println(measurement.summary(TimeUnit.MILLISECONDS, true));



            }

        }


    }

    private static class RunOptions {
        boolean showTokens, showAst, showMeasurements;
        boolean lintMode;
        boolean beautifyMode;
        int optimizationLevel;

        RunOptions() {
            showTokens = false;
            showAst = false;
            showMeasurements = false;
            lintMode = false;
            beautifyMode = false;
            optimizationLevel = 0;
        }

        void validate() {
            if (lintMode) {
                showTokens = false;
                showAst = false;
                showMeasurements = false;
                optimizationLevel = 0;
            }
        }
    }

}
