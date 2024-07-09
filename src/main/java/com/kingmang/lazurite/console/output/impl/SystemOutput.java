package com.kingmang.lazurite.console.output.impl;

import com.kingmang.lazurite.console.output.Output;

import java.io.File;

public class SystemOutput implements Output {

    private final PrintStreamOutput out = new PrintStreamOutput(System.out, System.err);

    @Override
    public String newline() {
        return System.lineSeparator();
    }

    @Override
    public void print(String value) {
        out.print(value);
    }

    @Override
    public void println(String value) {
        out.println(value);
    }

    @Override
    public String getText() {
        return "";
    }

    @Override
    public void error(CharSequence value) {
        out.error(value);
    }

    @Override
    public File fileInstance(String path) {
        return new File(path);
    }
}
