package com.kingmang.lazurite.runner;

public class RunnerInfo {
    public static String VERSION() {
        return "2.7.2";
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
}
