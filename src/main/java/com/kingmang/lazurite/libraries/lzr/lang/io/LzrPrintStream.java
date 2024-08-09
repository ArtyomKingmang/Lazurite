package com.kingmang.lazurite.libraries.lzr.lang.io;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.runtime.values.LzrNumber;
import com.kingmang.lazurite.runtime.values.LzrReference;
import com.kingmang.lazurite.runtime.values.LzrValue;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class LzrPrintStream {


    static class newPrintStream implements Function {
        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            try {
                Arguments.check(1, args.length);
                return new LzrReference(new PrintStream(args[0].asString()));
            } catch (FileNotFoundException e) {
                throw new LzrException("RuntimeException: ", e.getMessage());
            }
        }
    }

    static class PrintStreamOut implements Function{
        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            Arguments.check(1, args.length);
            LzrValue s = args[0];
            if (!(s instanceof LzrReference))
                throw new LzrException("BadArg", "expected reference as printStream object");
            PrintStream printStream = (PrintStream) ((LzrReference) s).getRef();
            printStream.print(args[0]);
            return LzrNumber.ZERO;
        }


    }
}


