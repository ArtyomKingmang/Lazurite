package com.kingmang.lazurite;

import java.io.FileWriter;

public class log {
    public static void append(String str) throws Exception {
        FileWriter writer = new FileWriter("log.txt", true);
        writer.write(str+"\n");
        writer.flush();
    }
    public static void clear() throws Exception {
        FileWriter writer = new FileWriter("log.txt", false);
        writer.write("");
        writer.flush();
    }
}