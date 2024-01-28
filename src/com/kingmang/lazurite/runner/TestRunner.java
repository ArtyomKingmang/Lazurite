package com.kingmang.lazurite.runner;

import com.kingmang.lazurite.utils.Handler;
import java.io.IOException;

public class TestRunner {
    public static void main(String[] args) throws IOException {
        StringBuilder builder = new StringBuilder();
        String path = "C:\\Users\\crowb\\Desktop\\Lazurite\\test\\";
        builder.append(path);

        String filename = "test.lzr";
        builder.append(filename);

        Handler.RUN(builder.toString());
    }
}
