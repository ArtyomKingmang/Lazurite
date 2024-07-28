package com.kingmang.lazurite.core;

import com.kingmang.lazurite.runtime.values.LzrFunction;
import com.kingmang.lazurite.runtime.values.LzrNumber;
import com.kingmang.lazurite.runtime.values.LzrString;
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


    public static LzrFunction voidToVoid(VoidToVoidFunction f) {
        return new LzrFunction(args -> {
            f.apply();
            return LzrNumber.ZERO;
        });
    }

    public static LzrFunction voidToBoolean(VoidToBooleanFunction f) {
        return new LzrFunction(args -> LzrNumber.fromBoolean(f.apply()));
    }

    public static LzrFunction voidToInt(VoidToIntFunction f) {
        return new LzrFunction(args -> LzrNumber.of(f.apply()));
    }

    public static LzrFunction voidToLong(VoidToLongFunction f) {
        return new LzrFunction(args -> LzrNumber.of(f.apply()));
    }

    public static LzrFunction voidToFloat(VoidToFloatFunction f) {
        return new LzrFunction(args -> LzrNumber.of(f.apply()));
    }

    public static LzrFunction voidToDouble(VoidToDoubleFunction f) {
        return new LzrFunction(args -> LzrNumber.of(f.apply()));
    }

    public static LzrFunction voidToCharSequence(VoidToCharSequenceFunction f) {
        return new LzrFunction(args -> new LzrString(f.apply().toString()));
    }
    
    public static LzrFunction voidToString(VoidToStringFunction f) {
        return new LzrFunction(args -> new LzrString(f.apply()));
    }

    public static <E extends Enum<E>> LzrFunction enumOrdinal(VoidToEnumFunction<E> f) {
        return new LzrFunction(args -> LzrNumber.of(f.apply().ordinal()));
    }

    public static LzrFunction booleanToVoid(BooleanToVoidFunction f) {
        return new LzrFunction(args -> {
            Arguments.check(1, args.length);
            f.apply(args[0].asInt() != 0);
            return LzrNumber.ZERO;
        });
    }

    public static LzrFunction booleanOptToVoid(BooleanToVoidFunction f) {
        return booleanOptToVoid(f, true);
    }
    public static LzrFunction booleanOptToVoid(BooleanToVoidFunction f, final boolean def) {
        return new LzrFunction(args -> {
            Arguments.checkOrOr(0, 1, args.length);
            f.apply( (args.length == 1) ? (args[0].asInt() != 0) : def );
            return LzrNumber.ZERO;
        });
    }

    public static LzrFunction intToVoid(IntToVoidFunction f) {
        return new LzrFunction(args -> {
            Arguments.check(1, args.length);
            f.apply(args[0].asInt());
            return LzrNumber.ZERO;
        });
    }
    
    public static LzrFunction intOptToVoid(VoidToVoidFunction f1, IntToVoidFunction f2) {
        return new LzrFunction(args -> {
            Arguments.checkOrOr(0, 1, args.length);
            if (args.length == 0) {
                f1.apply();
            } else {
                f2.apply(args[0].asInt());
            }
            return LzrNumber.ZERO;
        });
    }

    public static LzrFunction intToLong(IntToLongFunction f) {
        return new LzrFunction(args -> {
            Arguments.check(1, args.length);
            return LzrNumber.of(f.apply(args[0].asInt()));
        });
    }

    public static LzrFunction int2ToVoid(Int2ToVoidFunction f) {
        return new LzrFunction(args -> {
            Arguments.check(2, args.length);
            f.apply(args[0].asInt(),
                    args[1].asInt());
            return LzrNumber.ZERO;
        });
    }

    public static LzrFunction int4ToVoid(Int4ToVoidFunction f) {
        return new LzrFunction(args -> {
            Arguments.check(4, args.length);
            f.apply(args[0].asInt(),
                    args[1].asInt(),
                    args[2].asInt(),
                    args[3].asInt());
            return LzrNumber.ZERO;
        });
    }

    public static LzrFunction floatToVoid(FloatToVoidFunction f) {
        return new LzrFunction(args -> {
            Arguments.check(1, args.length);
            f.apply(ValueUtils.getFloatNumber(args[0]));
            return LzrNumber.ZERO;
        });
    }

    public static LzrFunction float4ToVoid(Float4ToVoidFunction f) {
        return new LzrFunction(args -> {
            Arguments.check(4, args.length);
            f.apply(ValueUtils.getFloatNumber(args[0]),
                    ValueUtils.getFloatNumber(args[1]),
                    ValueUtils.getFloatNumber(args[2]),
                    ValueUtils.getFloatNumber(args[3]));
            return LzrNumber.ZERO;
        });
    }

    public static LzrFunction doubleToVoid(DoubleToVoidFunction f) {
        return new LzrFunction(args -> {
            Arguments.check(1, args.length);
            f.apply(args[0].asNumber());
            return LzrNumber.ZERO;
        });
    }

    public static LzrFunction double2ToVoid(Double2ToVoidFunction f) {
        return new LzrFunction(args -> {
            Arguments.check(2, args.length);
            f.apply(args[0].asNumber(), args[1].asNumber());
            return LzrNumber.ZERO;
        });
    }

    public static LzrFunction double4ToVoid(Double4ToVoidFunction f) {
        return new LzrFunction(args -> {
            Arguments.check(4, args.length);
            f.apply(args[0].asNumber(), args[1].asNumber(),
                    args[2].asNumber(), args[3].asNumber());
            return LzrNumber.ZERO;
        });
    }

    public static LzrFunction charSequenceToVoid(CharSequenceToVoidFunction f) {
        return charSequenceToVoid(f, false);
    }

    public static LzrFunction charSequenceToVoid(CharSequenceToVoidFunction f, boolean emptyAsNull) {
        return new LzrFunction(args -> {
            Arguments.check(1, args.length);
            final String text = args[0].asString();
            if (emptyAsNull && (text != null) && (text.isEmpty())) {
                f.apply(null);
            } else {
                f.apply(text);
            }
            return LzrNumber.ZERO;
        });
    }

    public static LzrFunction stringToVoid(StringToVoidFunction f) {
        return new LzrFunction(args -> {
            Arguments.check(1, args.length);
            f.apply(args[0].asString());
            return LzrNumber.ZERO;
        });
    }

    public static LzrFunction stringToBoolean(Predicate<String> f) {
        return new LzrFunction(args -> {
            Arguments.check(1, args.length);
            return LzrNumber.fromBoolean(f.test(args[0].asString()));
        });
    }
}
