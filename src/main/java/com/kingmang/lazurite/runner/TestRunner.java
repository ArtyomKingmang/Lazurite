package com.kingmang.lazurite.runner;


import com.kingmang.lazurite.parser.parse.Lexer;
import com.kingmang.lazurite.parser.parse.Token;
import com.kingmang.lazurite.parser.parse.classes.LexerImplementation;
import com.kingmang.lazurite.utils.Handler;
import com.kingmang.lazurite.utils.Loader;

import java.io.IOException;
import java.util.List;

public class TestRunner {
    public static void main(String[] args) throws IOException {

        StringBuilder builder = new StringBuilder();
        String path = "test/";
        builder.append(path);

        String filename = "test.lzr";
        builder.append(filename);

        Handler.Run(builder.toString());

    }
}
