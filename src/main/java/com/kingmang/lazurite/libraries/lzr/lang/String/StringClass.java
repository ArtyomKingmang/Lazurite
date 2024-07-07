package com.kingmang.lazurite.libraries.lzr.lang.String;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.runtime.values.LzrString;
import com.kingmang.lazurite.runtime.values.LzrValue;

public final class StringClass {

        public static LzrValue valueOf(LzrValue[] args) {
            Arguments.check(1, args.length);
            return new LzrString(java.lang.String.valueOf(args[0]));

        }

        public static LzrValue format(LzrValue[] args) {
            Arguments.check(2, args.length);
            return new LzrString(java.lang.String.format(args[0].asString(), args[1]));

        }

        public static LzrValue join(LzrValue[] args) {
            Arguments.check(2, args.length);
            return new LzrString(java.lang.String.join((CharSequence) args[0], (CharSequence) args[1], (CharSequence) args[2]));

        }


    }