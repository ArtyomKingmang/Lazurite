package com.kingmang.lazurite.runner;

import lombok.Getter;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

public class RunnerInfo {
    @Getter
    private static final String Version = "2.7.4";


    public static void Console () {
        AnsiConsole.systemInstall();

        System.out.println(Ansi.ansi().fg(Ansi.Color.CYAN).a("\n\t-------" + getVersion() + "-------\n" +
                "\tLazurite Console" + "\n" +
                "\t-------------------").reset());
        AnsiConsole.out().println("------------------------------------------------------------");
        AnsiConsole.out().println(
                        "run / -r - asks for the path to the file and runs it\n" +
                        "version / -v - returns the version of Lazurite\n" +
                        "help / -h - show help commands\n" +
                        "editor / -e - open code editor\n" +
                        "new / -n - creates new project\n" +
                        "preprocess / -pr\n" +
                        "cls - -clears the command line\n"
        );
    }

    public static void Command () {
        System.out.println("\n\t-------" + getVersion() + "-------\n" +
                "\tLazurite Console" + "\n" +
                "\t-----------------");
        System.out.println("------------------------------------------------------------");
        System.out.println(
                "--run / -r - asks for the path to the file and runs it\n" +
                        "--version / -v - returns the version of Lazurite\n" +
                        "--help / -h - show help commands\n" +
                        "--editor / -e - open code editor\n" +
                        "--new / -n - creates new project\n" +
                        "cls - clears the command line\n"
        );
    }
}
