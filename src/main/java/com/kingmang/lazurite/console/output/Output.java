package com.kingmang.lazurite.console.output;

import java.io.File;

public interface Output {

    String newline();

    void print(String value);

    default void print(Object value) {
        print(value.toString());
    }

    default void println() {
        println(newline());
    }

    void println(String value);

    default void println(Object value) {
        println(value.toString());
    }

    String getText();

    default void error(Throwable throwable) {
        error(throwable.toString());
    }

    void error(CharSequence value);

    File fileInstance(String path);

}
