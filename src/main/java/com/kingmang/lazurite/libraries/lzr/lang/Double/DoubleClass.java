package com.kingmang.lazurite.libraries.lzr.lang.Double;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.runtime.values.LzrNumber;
import com.kingmang.lazurite.runtime.values.LzrValue;

public final class DoubleClass {
        public static LzrValue parseDouble(LzrValue[] args) {
            Arguments.check(1, args.length);
            return LzrNumber.of(java.lang.Double.parseDouble(args[0].asString()));

        }
        public static LzrValue compare(LzrValue[] args) {
            Arguments.check(2, args.length);
            return LzrNumber.of(java.lang.Double.compare(args[0].asInt(),args[1].asInt()));

        }
        public static LzrValue doubleToLongBits(LzrValue[] args) {
            Arguments.check(1, args.length);
            return LzrNumber.of(java.lang.Double.doubleToLongBits(args[0].asNumber()));

        }
        public static LzrValue max(LzrValue[] args) {
            Arguments.check(2, args.length);
            return LzrNumber.of(java.lang.Double.max(args[0].asInt(), args[1].asInt()));

        }

        public static LzrValue min(LzrValue[] args) {
            Arguments.check(2, args.length);
            return LzrNumber.of(java.lang.Double.min(args[0].asInt(), args[1].asInt()));

        }
    }