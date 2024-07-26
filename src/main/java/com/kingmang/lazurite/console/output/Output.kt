package com.kingmang.lazurite.console.output;

import java.io.File;

public interface Output {

    String newline();

    void print(String value);

    default void print(Object value) {
        print(value.toString());
    }

    default void println() {
        print(newline());
    }

    default void println(String value) {
        print(value);
        println();
    }

    default void println(Object value) {
        println(value.toString());
    }

    default void error(Throwable throwable) {
        error(throwable.toString());
    }

    void error(CharSequence value);

    File fileInstance(String path);

}
