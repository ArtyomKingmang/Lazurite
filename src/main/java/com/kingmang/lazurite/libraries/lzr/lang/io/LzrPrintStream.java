package com.kingmang.lazurite.libraries.lzr.lang.io;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.runtime.values.LzrNumber;
import com.kingmang.lazurite.runtime.values.LzrValue;
import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.PrintStream;

public class LzrPrintStream {

    private static PrintStream printStream;

    static class newPrintStream implements Function {
        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            try {
                printStream = new PrintStream(args[0].asString());
            } catch (FileNotFoundException e) {
                throw new LzrException("RuntimeException: ", e.getMessage());
            }
            return LzrNumber.ZERO;
        }


    }

    static class PrintStreamOut implements Function{
        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            printStream.println(args[0]);
            return LzrNumber.ZERO;
        }


    }
}


