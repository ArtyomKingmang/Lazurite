package com.kingmang.lazurite.libraries.lzr.lang.io;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.libraries.lzr.utils.measurement.TimeMeasurement;
import com.kingmang.lazurite.runtime.values.LzrNumber;
import com.kingmang.lazurite.runtime.values.LzrReference;
import com.kingmang.lazurite.runtime.values.LzrString;
import com.kingmang.lazurite.runtime.values.LzrValue;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayInputStream;

public class LzrByteArrayInputStream {



    static class newByteArrayInputStream implements Function {
        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            Arguments.check(1, args.length);
            byte[] result = args[0].asString().getBytes();
            return new LzrReference(new ByteArrayInputStream(result));
        }
    }

    static class readByteArrayInputStream implements Function {
        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            Arguments.check(1, args.length);
            LzrValue s = args[0];
            if (!(s instanceof LzrReference))
                throw new LzrException("BadArg", "expected reference as byteArrayInputStream object");
            ByteArrayInputStream byteArrayInputStream = (ByteArrayInputStream) ((LzrReference) s).getRef();
            int read = byteArrayInputStream.read();
            return new LzrNumber(read);
        }
    }
    static class availableByteArrayInputStream implements Function {
        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            Arguments.check(1, args.length);
            LzrValue s = args[0];
            if (!(s instanceof LzrReference))
                throw new LzrException("BadArg", "expected reference as byteArrayInputStream object");
            ByteArrayInputStream byteArrayInputStream = (ByteArrayInputStream) ((LzrReference) s).getRef();
            int resultOfAvailable = byteArrayInputStream.available();
            return new LzrNumber(resultOfAvailable);
        }
    }

    static class readNBytesByteArrayInputStream implements Function {
        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            Arguments.check(4, args.length);
            LzrValue s = args[0];
            if (!(s instanceof LzrReference))
                throw new LzrException("BadArg", "expected reference as byteArrayInputStream object");
            ByteArrayInputStream byteArrayInputStream = (ByteArrayInputStream) ((LzrReference) s).getRef();
            byte[] bytes = args[1].asString().getBytes();
            int resultOfReading = byteArrayInputStream.read(bytes, args[2].asInt(), args[3].asInt());
            return new LzrNumber(resultOfReading);
        }
    }
}
