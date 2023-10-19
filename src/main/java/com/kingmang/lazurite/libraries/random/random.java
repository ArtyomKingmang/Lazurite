package com.kingmang.lazurite.libraries.random;


import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.core.KEYWORD;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
import com.kingmang.lazurite.runtime.Value;

import java.util.Random;

public final class random implements Library {


    @Override
    public void init() {
        KEYWORD.put("random", new randomm());
    }

    public final class randomm implements Function {

        private final Random RND = new Random();

        @Override
        public Value execute(Value... args) {
            Arguments.checkRange(0, 2, args.length);
            if (args.length == 0) return LZRNumber.of(RND.nextDouble());

            final Object raw = args[0].raw();
            if (raw instanceof Long) {
                long from = 0L;
                long to = 100L;
                if (args.length == 1) {
                    to = (long) raw;
                } else if (args.length == 2) {
                    from = (long) raw;
                    to = ((LZRNumber) args[1]).asLong();
                }
                final long randomLong = RND.nextLong() >>> 1;
                return LZRNumber.of(randomLong % (to - from) + from);
            }

            int from = 0;
            int to = 100;
            if (args.length == 1) {
                to = args[0].asInt();
            } else if (args.length == 2) {
                from = args[0].asInt();
                to = args[1].asInt();
            }
            return LZRNumber.of(RND.nextInt(to - from) + from);
        }
    }

}
