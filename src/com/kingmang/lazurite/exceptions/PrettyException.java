package com.kingmang.lazurite.exceptions;

import com.kingmang.lazurite.console.Console;
import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.ansi;

public class PrettyException {
    protected static void message(String name, String message){
        Console.println("\n--------"+name+"----------");
        Console.println(ansi().fg(Ansi.Color.RED).a(message).reset());
        Console.println("------------------------------------------------\n");
        Console.println("----------------Java Exception------------------");
    }
}
