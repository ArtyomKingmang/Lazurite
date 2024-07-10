package com.kingmang.lazurite.exceptions;

import com.kingmang.lazurite.console.Console;
import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.ansi;

public class PrettyException {
    static String tabsCount = "\t";
    protected static void message(boolean check,String name, String message){
        if(check) {
            char[] chars = new char[name.length()];
            if(chars.length <= 12){
                tabsCount = "\t\t\t\t";
            }else if(chars.length <= 22){
                tabsCount = "\t\t\t";
            }else {
                tabsCount = "\t";
            }
            Console.print("-------------------------------------------");
            Console.println("\n" + tabsCount + name);
            Console.print("-------------------------------------------\n");
            Console.println(ansi().fg(Ansi.Color.RED).a(message).reset());
            Console.println("--------------------------------------------\n");
            //Console.println("----------------Java Exception------------------");
        }
    }
}
