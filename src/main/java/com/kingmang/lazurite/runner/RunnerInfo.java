package com.kingmang.lazurite.runner;

import lombok.Getter;
import lombok.Setter;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

public class RunnerInfo {

    @Getter
    private static final String Version = "2.7.5";

    @Setter
    @Getter
    private static String pathToLzrLibs = "lzrlibs/";

    public static void Console () {
        AnsiConsole.systemInstall();

        System.out.println(Ansi.ansi().fg(Ansi.Color.CYAN).a("\n\t-------" + getVersion() + "-------\n" +
                "\tLazurite Console" + "\n" +
                "\t-------------------").reset());
        AnsiConsole.out().println("------------------------------------------------------------");
        AnsiConsole.out().println(
                """
                        run / -r - asks for the path to the file and runs it
                        version / -v - returns the version of Lazurite
                        help / -h - show help commands
                        editor / -e - open code editor
                        new / -n - creates new project
                        cls - -clears the command line
                        """
        );
    }

    public static void Command () {
        System.out.println("\n\t-------" + getVersion() + "-------\n" +
                "\tLazurite Console" + "\n" +
                "\t-----------------");
        System.out.println("------------------------------------------------------------");
        System.out.println(
                """
                        --run / -r - asks for the path to the file and runs it
                        --version / -v - returns the version of Lazurite
                        --help / -h - show help commands
                        --editor / -e - open code editor
                        --new / -n - creates new project
                        cls - clears the command line
                        """
        );
    }


}
