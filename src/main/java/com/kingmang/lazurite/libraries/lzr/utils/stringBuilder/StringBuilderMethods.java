package com.kingmang.lazurite.libraries.lzr.utils.stringBuilder;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.runtime.values.LzrNumber;
import com.kingmang.lazurite.runtime.values.LzrString;
import com.kingmang.lazurite.runtime.values.LzrValue;

public class StringBuilderMethods {

        static StringBuilder stringBuilder;

        //------------------stringBuilder-----------------------

        public static LzrValue newBuilder(LzrValue[] args) {
            Arguments.check(0,  args.length);
            stringBuilder = new StringBuilder();
            return LzrNumber.ZERO;
        }

        public static LzrValue addToBuilder(LzrValue[] args) {
            Arguments.check(1,  args.length);
            stringBuilder.append(args[0]);
            return LzrNumber.ZERO;
        }
        public static LzrValue deleteBuilder(LzrValue[] args) {
            Arguments.check(1,  args.length);
            stringBuilder.delete(args[0].asInt(), args[1].asInt());
            return LzrNumber.ZERO;
        }

        public static LzrValue deleteCharAtBuilder(LzrValue[] args) {
            Arguments.check(1,  args.length);
            stringBuilder.deleteCharAt(args[0].asInt());
            return LzrNumber.ZERO;
        }
        public static LzrValue toStrBuilder(LzrValue[] args) {
            return new LzrString(stringBuilder.toString());
        }

    }