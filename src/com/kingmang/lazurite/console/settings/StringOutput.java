package com.kingmang.lazurite.console.settings;

import com.kingmang.lazurite.console.settings.Output;

import java.io.File;

public class StringOutput implements Output {

    private final StringBuffer out, err;

    public StringOutput() {
        this(new StringBuffer());
    }

    public StringOutput(StringBuffer out) {
        this(out, out);
    }

    public StringOutput(StringBuffer out, StringBuffer err) {
        this.out = out;
        this.err = err;
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
    public void print(Object value) {
        out.append(value.toString());
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
    public void println(Object value) {
        println(value.toString());
    }

    @Override
    public String getText() {
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
