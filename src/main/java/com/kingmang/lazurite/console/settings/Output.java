package com.kingmang.lazurite.console.settings;

import java.io.File;

public interface Output {

    String newline();

    void print(String value);

    void print(Object value);

    void println();

    void println(String value);

    void println(Object value);

    String getText();

    void error(Throwable throwable);

    void error(CharSequence value);

    File fileInstance(String path);

}
