package com.kingmang.lazurite.console.output.impl;

import com.kingmang.lazurite.console.output.Output;
import lombok.AllArgsConstructor;

import java.io.File;

@AllArgsConstructor
public class StringOutput implements Output {

    private final StringBuffer out, err;

    public StringOutput() {
        this(new StringBuffer());
    }

    public StringOutput(StringBuffer out) {
        this(out, out);
    }

    @Override
    public String newline() {
        return System.lineSeparator();
    }

    @Override
    public void print(String value) {
        out.append(value);
    }

    @Override
    public void println() {
        out.append(newline());
    }

    @Override
    public void println(String value) {
        out.append(value).append(newline());
    }

    @Override
    public String toString() {
        return out.toString();
    }

    @Override
    public void error(Throwable throwable) {
        error(throwable.toString());
    }

    @Override
    public void error(CharSequence value) {
        err.append(value).append(newline());
    }

    @Override
    public File fileInstance(String path) {
        return new File(path);
    }
}
