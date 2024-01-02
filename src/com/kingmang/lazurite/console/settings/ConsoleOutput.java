package com.kingmang.lazurite.console.settings;

import com.kingmang.lazurite.runtime.LZR.LZRString;

import java.io.File;
import java.util.Scanner;

public class ConsoleOutput implements Output {

    @Override
    public String newline() {
        return System.lineSeparator();
    }

    @Override
    public void print(String value) {
        System.out.print(value);
    }

    @Override
    public void print(Object value) {
        print(value.toString());
    }


    @Override
    public void println() {
        System.out.println();
    }

    @Override
    public void println(String value) {
        System.out.println(value);
    }

    @Override
    public void println(Object value) {
        println(value.toString());
    }

    @Override
    public String getText() {
        return "";
    }

    @Override
    public void error(Throwable throwable) {
        error(throwable.toString());
    }

    @Override
    public void error(CharSequence value) {
        System.err.println(value);
    }

    @Override
    public File fileInstance(String path) {
        return new File(path);
    }
}
