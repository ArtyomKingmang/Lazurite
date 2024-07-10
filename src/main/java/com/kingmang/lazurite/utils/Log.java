package com.kingmang.lazurite.utils;

import java.io.FileWriter;

public class Log {
    public static void append(String str) throws Exception {
        try (FileWriter writer = new FileWriter("log.txt", true)) {
            writer.write(str+"\n");
            writer.flush();
        }
    }
    public static void clear() throws Exception {
        try (FileWriter writer = new FileWriter("log.txt", false)) {

            writer.write("");
            writer.flush();
        }
    }
}