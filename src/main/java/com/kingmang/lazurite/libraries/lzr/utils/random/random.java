package com.kingmang.lazurite.libraries.lzr.utils.random;


import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.Keyword;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.values.LzrNumber;
import com.kingmang.lazurite.runtime.values.LzrValue;

import java.util.Random;

public final class random implements Library {


    @Override
    public void init() {
        Keyword.put("random", new randomm());
    }

    public final class randomm implements Function {

        private final Random RND = new Random();

        @Override
        public LzrValue execute(LzrValue... args) {
            Arguments.checkRange(0, 2, args.length);
            if (args.length == 0) return LzrNumber.of(RND.nextDouble());

            final Object raw = args[0].raw();
            if (raw instanceof Long) {
                long from = 0L;
                long to = 100L;
                if (args.length == 1) {
                    to = (long) raw;
                } else if (args.length == 2) {
                    from = (long) raw;
                    to = ((LzrNumber) args[1]).asLong();
                }
                final long randomLong = RND.nextLong() >>> 1;
                return LzrNumber.of(randomLong % (to - from) + from);
            }

            int from = 0;
            int to = 100;
            if (args.length == 1) {
                to = args[0].asInt();
            } else if (args.length == 2) {
                from = args[0].asInt();
                to = args[1].asInt();
            }
            return LzrNumber.of(RND.nextInt(to - from) + from);
        }
    }

}
