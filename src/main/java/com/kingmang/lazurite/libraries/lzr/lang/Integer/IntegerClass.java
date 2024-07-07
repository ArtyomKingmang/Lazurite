package com.kingmang.lazurite.libraries.lzr.lang.Integer;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.runtime.values.LzrNumber;
import com.kingmang.lazurite.runtime.values.LzrValue;

public final class IntegerClass {

        public static LzrValue parseInt(LzrValue[] args) {
            Arguments.checkOrOr(1, 2, args.length);
            final int radix = (args.length == 2) ? args[1].asInt() : 10;
            return LzrNumber.of(java.lang.Integer.parseInt(args[0].asString(), radix));

        }

        public static LzrValue compare(LzrValue[] args) {
            Arguments.check(2, args.length);
            return LzrNumber.of(java.lang.Integer.compare(args[0].asInt(),args[1].asInt()));

        }

        public static LzrValue bitCount(LzrValue[] args) {
            Arguments.check(1, args.length);

            return LzrNumber.of(java.lang.Integer.bitCount(args[0].asInt()));

        }

        public static LzrValue signum(LzrValue[] args) {
            Arguments.check(1, args.length);
            return LzrNumber.of(java.lang.Integer.signum(args[0].asInt()));

        }

        public static LzrValue decode(LzrValue[] args) {
            Arguments.check(1, args.length);
            return LzrNumber.of(java.lang.Integer.decode(args[0].asString()));

        }

        public static LzrValue compareUnsigned(LzrValue[] args) {
            Arguments.check(2, args.length);
            return LzrNumber.of(java.lang.Integer.compareUnsigned(args[0].asInt(), args[1].asInt()));

        }

        public static LzrValue max(LzrValue[] args) {
            Arguments.check(2, args.length);
            return LzrNumber.of(java.lang.Integer.max(args[0].asInt(), args[1].asInt()));

        }

        public static LzrValue min(LzrValue[] args) {
            Arguments.check(2, args.length);
            return LzrNumber.of(java.lang.Integer.min(args[0].asInt(), args[1].asInt()));

        }
    }