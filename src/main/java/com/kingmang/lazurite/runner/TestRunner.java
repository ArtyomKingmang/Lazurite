package com.kingmang.lazurite.runner;

import com.kingmang.lazurite.utils.Handler;

import java.io.IOException;

public class TestRunner {
    public static void main(String[] args) throws IOException {

        String path = "test/";

        String filename = "test.lzr";
        String builder = path +
                filename;

        Handler.Run(builder);

    }
}
