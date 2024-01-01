package com.kingmang.lazurite.core;

import com.kingmang.lazurite.runtime.LZR.LZRFunction;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
import com.kingmang.lazurite.runtime.LZR.LZRString;
import com.kingmang.lazurite.utils.ValueUtils;

import java.util.function.Predicate;

public final class Converters {

    public interface VoidToVoidFunction {
        void apply();
    }

    public interface VoidToBooleanFunction {
        boolean apply();
    }

    public interface VoidToIntFunction {
        int apply();
    }

    public interface VoidToLongFunction {
        long apply();
    }

    public interface VoidToFloatFunction {
        float apply();
    }

    public interface VoidToDoubleFunction {
        double apply();
    }

    public interface VoidToCharSequenceFunction {
        CharSequence apply();
    }

    public interface VoidToStringFunction {
        String apply();
    }

    public interface VoidToEnumFunction<E extends Enum<E>> {
        Enum<E> apply();
    }

    public interface BooleanToVoidFunction {
        void apply(boolean b);
    }

    public interface IntToVoidFunction {
        void apply(int i);
    }

    public interface IntToLongFunction {
        long apply(int i);
    }

    public interface Int2ToVoidFunction {
        void apply(int i1, int i2);
    }

    public interface Int4ToVoidFunction {
        void apply(int i1, int i2, int i3, int i4);
    }

    public interface FloatToVoidFunction {
        void apply(float f);
    }

    public interface Float4ToVoidFunction {
        void apply(float f1, float f2, float f3, float f4);
    }

    public interface DoubleToVoidFunction {
        void apply(double d);
    }

    public interface Double2ToVoidFunction {
        void apply(double d1, double d2);
    }

    public interface Double4ToVoidFunction {
        void apply(double d1, double d2, double d3, double d4);
    }

    public interface CharSequenceToVoidFunction {
        void apply(CharSequence s);
    }

    public interface StringToVoidFunction {
        void apply(String s);
    }


    public static LZRFunction voidToVoid(VoidToVoidFunction f) {
        return new LZRFunction(args -> {
            f.apply();
            return LZRNumber.ZERO;
        });
    }

    public static LZRFunction voidToBoolean(VoidToBooleanFunction f) {
        return new LZRFunction(args -> LZRNumber.fromBoolean(f.apply()));
    }

    public static LZRFunction voidToInt(VoidToIntFunction f) {
        return new LZRFunction(args -> LZRNumber.of(f.apply()));
    }

    public static LZRFunction voidToLong(VoidToLongFunction f) {
        return new LZRFunction(args -> LZRNumber.of(f.apply()));
    }

    public static LZRFunction voidToFloat(VoidToFloatFunction f) {
        return new LZRFunction(args -> LZRNumber.of(f.apply()));
    }

    public static LZRFunction voidToDouble(VoidToDoubleFunction f) {
        return new LZRFunction(args -> LZRNumber.of(f.apply()));
    }

    public static LZRFunction voidToCharSequence(VoidToCharSequenceFunction f) {
        return new LZRFunction(args -> new LZRString(f.apply().toString()));
    }
    
    public static LZRFunction voidToString(VoidToStringFunction f) {
        return new LZRFunction(args -> new LZRString(f.apply()));
    }

    public static <E extends Enum<E>> LZRFunction enumOrdinal(VoidToEnumFunction<E> f) {
        return new LZRFunction(args -> LZRNumber.of(f.apply().ordinal()));
    }

    public static LZRFunction booleanToVoid(BooleanToVoidFunction f) {
        return new LZRFunction(args -> {
            Arguments.check(1, args.length);
            f.apply(args[0].asInt() != 0);
            return LZRNumber.ZERO;
        });
    }

    public static LZRFunction booleanOptToVoid(BooleanToVoidFunction f) {
        return booleanOptToVoid(f, true);
    }
    public static LZRFunction booleanOptToVoid(BooleanToVoidFunction f, final boolean def) {
        return new LZRFunction(args -> {
            Arguments.checkOrOr(0, 1, args.length);
            f.apply( (args.length == 1) ? (args[0].asInt() != 0) : def );
            return LZRNumber.ZERO;
        });
    }

    public static LZRFunction intToVoid(IntToVoidFunction f) {
        return new LZRFunction(args -> {
            Arguments.check(1, args.length);
            f.apply(args[0].asInt());
            return LZRNumber.ZERO;
        });
    }
    
    public static LZRFunction intOptToVoid(VoidToVoidFunction f1, IntToVoidFunction f2) {
        return new LZRFunction(args -> {
            Arguments.checkOrOr(0, 1, args.length);
            if (args.length == 0) {
                f1.apply();
            } else {
                f2.apply(args[0].asInt());
            }
            return LZRNumber.ZERO;
        });
    }

    public static LZRFunction intToLong(IntToLongFunction f) {
        return new LZRFunction(args -> {
            Arguments.check(1, args.length);
            return LZRNumber.of(f.apply(args[0].asInt()));
        });
    }

    public static LZRFunction int2ToVoid(Int2ToVoidFunction f) {
        return new LZRFunction(args -> {
            Arguments.check(2, args.length);
            f.apply(args[0].asInt(),
                    args[1].asInt());
            return LZRNumber.ZERO;
        });
    }

    public static LZRFunction int4ToVoid(Int4ToVoidFunction f) {
        return new LZRFunction(args -> {
            Arguments.check(4, args.length);
            f.apply(args[0].asInt(),
                    args[1].asInt(),
                    args[2].asInt(),
                    args[3].asInt());
            return LZRNumber.ZERO;
        });
    }

    public static LZRFunction floatToVoid(FloatToVoidFunction f) {
        return new LZRFunction(args -> {
            Arguments.check(1, args.length);
            f.apply(com.kingmang.lazurite.utils.ValueUtils.getFloatNumber(args[0]));
            return LZRNumber.ZERO;
        });
    }

    public static LZRFunction float4ToVoid(Float4ToVoidFunction f) {
        return new LZRFunction(args -> {
            Arguments.check(4, args.length);
            f.apply(com.kingmang.lazurite.utils.ValueUtils.getFloatNumber(args[0]),
                    com.kingmang.lazurite.utils.ValueUtils.getFloatNumber(args[1]),
                    com.kingmang.lazurite.utils.ValueUtils.getFloatNumber(args[2]),
                    ValueUtils.getFloatNumber(args[3]));
            return LZRNumber.ZERO;
        });
    }

    public static LZRFunction doubleToVoid(DoubleToVoidFunction f) {
        return new LZRFunction(args -> {
            Arguments.check(1, args.length);
            f.apply(args[0].asNumber());
            return LZRNumber.ZERO;
        });
    }

    public static LZRFunction double2ToVoid(Double2ToVoidFunction f) {
        return new LZRFunction(args -> {
            Arguments.check(2, args.length);
            f.apply(args[0].asNumber(), args[1].asNumber());
            return LZRNumber.ZERO;
        });
    }

    public static LZRFunction double4ToVoid(Double4ToVoidFunction f) {
        return new LZRFunction(args -> {
            Arguments.check(4, args.length);
            f.apply(args[0].asNumber(), args[1].asNumber(),
                    args[2].asNumber(), args[3].asNumber());
            return LZRNumber.ZERO;
        });
    }

    public static LZRFunction charSequenceToVoid(CharSequenceToVoidFunction f) {
        return charSequenceToVoid(f, false);
    }

    public static LZRFunction charSequenceToVoid(CharSequenceToVoidFunction f, boolean emptyAsNull) {
        return new LZRFunction(args -> {
            Arguments.check(1, args.length);
            final String text = args[0].asString();
            if (emptyAsNull && (text != null) && (text.isEmpty())) {
                f.apply(null);
            } else {
                f.apply(text);
            }
            return LZRNumber.ZERO;
        });
    }

    public static LZRFunction stringToVoid(StringToVoidFunction f) {
        return new LZRFunction(args -> {
            Arguments.check(1, args.length);
            f.apply(args[0].asString());
            return LZRNumber.ZERO;
        });
    }

    public static LZRFunction stringToBoolean(Predicate<String> f) {
        return new LZRFunction(args -> {
            Arguments.check(1, args.length);
            return LZRNumber.fromBoolean(f.test(args[0].asString()));
        });
    }
}
