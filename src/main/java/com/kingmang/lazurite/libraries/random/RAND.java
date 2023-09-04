package com.kingmang.lazurite.libraries.random;

import com.kingmang.lazurite.base.Arguments;
import com.kingmang.lazurite.base.Function;
import com.kingmang.lazurite.runtime.NumberValue;
import com.kingmang.lazurite.runtime.Value;

import java.util.Random;

public final class RAND implements Function {

    private static final Random RND = new Random();

    @Override
    public Value execute(Value... args) {
        Arguments.checkRange(0, 2, args.length);
        if (args.length == 0) return NumberValue.of(RND.nextDouble());

        final Object raw = args[0].raw();
        if (raw instanceof Long) {
            long from = 0L;
            long to = 100L;
            if (args.length == 1) {
                to = (long) raw;
            } else if (args.length == 2) {
                from = (long) raw;
                to = ((NumberValue) args[1]).asLong();
            }
            final long randomLong = RND.nextLong() >>> 1;
            return NumberValue.of(randomLong % (to - from) + from);
        }

        int from = 0;
        int to = 100;
        if (args.length == 1) {
            to = args[0].asInt();
        } else if (args.length == 2) {
            from = args[0].asInt();
            to = args[1].asInt();
        }
        return NumberValue.of(RND.nextInt(to - from) + from);
    }
}
