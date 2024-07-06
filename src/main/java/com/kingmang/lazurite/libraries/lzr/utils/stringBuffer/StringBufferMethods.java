package com.kingmang.lazurite.libraries.lzr.utils.stringBuffer;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.runtime.values.LzrNumber;
import com.kingmang.lazurite.runtime.values.LzrString;
import com.kingmang.lazurite.runtime.values.LzrValue;

public class StringBufferMethods {
        static StringBuffer stringBuffer;

        //------------------stringBuffer-----------------------
        public static LzrValue newBuffer(LzrValue[] args) {
            stringBuffer = new StringBuffer();
            return LzrNumber.ZERO;
        }

        public static LzrValue addToBuffer(LzrValue[] args) {
            Arguments.check(1,  args.length);
            stringBuffer.append(args[0]);
            return LzrNumber.ZERO;
        }

        public static LzrValue toStrBuffer(LzrValue[] args) {
            return new LzrString(stringBuffer.toString());
        }

        public static LzrValue deleteBuffer(LzrValue[] args) {
            Arguments.check(1,  args.length);
            stringBuffer.delete(args[0].asInt(), args[1].asInt());
            return LzrNumber.ZERO;
        }

        public static LzrValue deleteCharAtBuffer(LzrValue[] args) {
            Arguments.check(1,  args.length);
            stringBuffer.deleteCharAt(args[0].asInt());
            return LzrNumber.ZERO;
        }
    }