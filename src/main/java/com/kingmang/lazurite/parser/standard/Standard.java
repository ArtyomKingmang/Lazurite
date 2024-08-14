package com.kingmang.lazurite.parser.standard;

import com.kingmang.lazurite.console.Console;
import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.runtime.ClassInstanceValue;
import com.kingmang.lazurite.runtime.UserDefinedFunction;
import com.kingmang.lazurite.runtime.values.*;
import com.kingmang.lazurite.utils.ValueUtils;
import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.util.*;

public class Standard {

    public static final class charAt implements Function {
        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            Arguments.check(2, args.length);

            final String input = args[0].asString();
            final int index = args[1].asInt();
            return LzrNumber.of((short)input.charAt(index));
        }
    }

    public static final class echo implements Function {

        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            final StringBuilder sb = new StringBuilder();
            for (LzrValue arg : args) {
                sb.append(arg.asString());
                sb.append(" ");
            }
            Console.println(sb.toString());
            return LzrNumber.ZERO;
        }
    }

    public static final class sortBy implements Function{
        @NotNull
        @Override
        public LzrValue execute(@NotNull LzrValue... args) {
            Arguments.check(2, args.length);
            if (args[0].type() != Types.ARRAY) {
                throw new LzrException("Type Exception", "Array expected at first argument");
            }
            final LzrValue[] elements = ((LzrArray) args[0]).getCopyElements();
            final Function function = ValueUtils.consumeFunction(args[1], 1);
            Arrays.sort(elements, Comparator.comparing(function::execute));
            return new LzrArray(elements);
        }
    }

    public static class combine implements Function {

        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            Arguments.checkAtLeast(1, args.length);
            Function result = null;
            for (LzrValue arg : args) {
                if (arg.type() != Types.FUNCTION) {
                    throw new LzrException("TypeException ", arg + " is not a function");
                }
                final Function current = result;
                final Function next = ((LzrFunction) arg).getValue();
                result = fArgs -> {
                    if (current == null) return next.execute(fArgs);
                    return next.execute(current.execute(fArgs));
                };
            }

            return new LzrFunction(Objects.requireNonNull(result, "Not found any function in arguments"));
        }

    }

    public static final class input implements Function {
        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            Scanner sc = new Scanner(System.in);
            return new LzrString(sc.nextLine());
        }
    }

    public static final class equal implements Function {
        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
           boolean check = args[0].equals(args[1]);
           return LzrNumber.fromBoolean(check);
        }
    }
    public static final class Array implements Function {

        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            if (args.length == 1)
                if (args[0].type() == Types.CLASS) {
                    ClassInstanceValue classInstance = (ClassInstanceValue) args[0];
                    return classInstance.callMethod("__array__");
                }
            return createArray(args, 0);
        }

        @NotNull
        private LzrArray createArray(@NotNull LzrValue[] args, int index) {
            final int size = args[index].asInt();
            final int last = args.length - 1;
            if (index == last) {
                return new LzrArray(size, unused -> LzrNumber.ZERO);
            } else if (index < last) {
                return new LzrArray(size, unused -> createArray(args, index + 1));
            }
            throw new IllegalStateException(String.format("Can't create array %d, %d, %d", size, index, last));
        }
    }
    public static final class string {

        private string() { }

        public static LzrArray getBytes(LzrValue[] args) {
            Arguments.checkOrOr(1, 2, args.length);
            final String charset = (args.length == 2) ? args[1].asString() : "UTF-8";
            if (args[0].type() == Types.CLASS) {
                LzrValue value = ((ClassInstanceValue) args[0]).callMethod("__bytes__");
                if (value.type() == Types.ARRAY) {
                    return (LzrArray) value;
                } else {
                    throw new LzrException("TypeException", "Expected array in method __bytes__, but got " + Types.typeToString(value.type()));
                }
            }
            try {
                return LzrArray.of(args[0].asString().getBytes(charset));
            } catch (UnsupportedEncodingException uee) {
                throw new RuntimeException(uee);
            }
        }
    }

    public static final class length implements Function {

        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            Arguments.check(1, args.length);

            final LzrValue val = args[0];
            final int length;
            switch (val.type()) {
                case Types.STRING:
                    length = ((LzrString) val).length();
                    break;
                case Types.ARRAY:
                    length = ((LzrArray) val).size();
                    break;
                case Types.MAP:
                    length = ((LzrMap) val).size();
                    break;
                case Types.FUNCTION:
                    final Function func = ((LzrFunction) val).getValue();
                    if (func instanceof UserDefinedFunction) {
                        length = ((UserDefinedFunction) func).getArgsCount();
                    } else {
                        length = 0;
                    }
                    break;
                case Types.CLASS:
                    ClassInstanceValue classInstance = (ClassInstanceValue) val;
                    LzrValue value = classInstance.callMethod("__length__");
                    length = value.asInt();
                    break;
                default:
                    length = 0;

            }
            return LzrNumber.of(length);
        }
    }

    public static final class sprintf implements Function {

        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            Arguments.checkAtLeast(1, args.length);

            final String format = args[0].asString();
            final Object[] values = new Object[args.length - 1];
            for (int i = 1; i < args.length; i++) {
                values[i - 1] = (args[i].type() == Types.NUMBER)
                        ? args[i].raw()
                        : args[i].asString();
            }
            return new LzrString(String.format(format, values));
        }
    }

    public static final class substr implements Function {

        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            Arguments.checkOrOr(2, 3, args.length);

            final String input = args[0].asString();
            final int startIndex = args[1].asInt();

            String result;
            if (args.length == 2) {
                result = input.substring(startIndex);
            } else {
                final int endIndex = args[2].asInt();
                result = input.substring(startIndex, endIndex);
            }

            return new LzrString(result);
        }
    }


    public static final class foreach implements Function {

        private static final int UNKNOWN = -1;

        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            Arguments.check(2, args.length);
            final LzrValue container = args[0];
            final Function consumer = ValueUtils.consumeFunction(args[1], 1);
            final int argsCount;
            if (consumer instanceof UserDefinedFunction) {
                argsCount = ((UserDefinedFunction) consumer).getArgsCount();
            } else {
                argsCount = UNKNOWN;
            }

            switch (container.type()) {
                case Types.STRING:
                    final LzrString string = (LzrString) container;
                    if (argsCount == 2) {
                        for (char ch : string.asString().toCharArray()) {
                            consumer.execute(new LzrString(String.valueOf(ch)), LzrNumber.of(ch));
                        }
                    } else {
                        for (char ch : string.asString().toCharArray()) {
                            consumer.execute(new LzrString(String.valueOf(ch)));
                        }
                    }
                    return string;

                case Types.ARRAY:
                    final LzrArray array = (LzrArray) container;
                    if (argsCount == 2) {
                        int index = 0;
                        for (LzrValue element : array) {
                            consumer.execute(element, LzrNumber.of(index++));
                        }
                    } else {
                        for (LzrValue element : array) {
                            consumer.execute(element);
                        }
                    }
                    return array;

                case Types.MAP:
                    final LzrMap map = (LzrMap) container;
                    for (Map.Entry<LzrValue, LzrValue> element : map) {
                        consumer.execute(element.getKey(), element.getValue());
                    }
                    return map;

                default:
                    throw new LzrException("TypeException ","Cannot iterate " + Types.typeToString(container.type()));
            }
        }
    }


    public static final class filter implements Function {

        private final boolean takeWhile;

        public filter(boolean takeWhile) {
            this.takeWhile = takeWhile;
        }

        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            Arguments.check(2, args.length);
            final LzrValue container = args[0];
            final Function predicate = ValueUtils.consumeFunction(args[1], 1);
            if (container.type() == Types.ARRAY) {
                return filterArray((LzrArray) container, predicate, takeWhile);
            }

            if (container.type() == Types.MAP) {
                return filterMap((LzrMap) container, predicate, takeWhile);
            }

            throw new LzrException("TypeException", "Invalid first argument. Array or map expected");
        }

        private LzrValue filterArray(LzrArray array, Function predicate, boolean takeWhile) {
            final int size = array.size();
            final List<LzrValue> values = new ArrayList<>(size);
            for (LzrValue value : array) {
                if (predicate.execute(value) != LzrNumber.ZERO) {
                    values.add(value);
                } else if (takeWhile) break;
            }
            final int newSize = values.size();
            return new LzrArray(values.toArray(new LzrValue[newSize]));
        }

        private LzrValue filterMap(LzrMap map, Function predicate, boolean takeWhile) {
            final LzrMap result = new LzrMap(map.size());
            for (Map.Entry<LzrValue, LzrValue> element : map) {
                if (predicate.execute(element.getKey(), element.getValue()) != LzrNumber.ZERO) {
                    result.set(element.getKey(), element.getValue());
                } else if (takeWhile) break;
            }
            return result;
        }
    }

    public static final class split implements Function {

        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            Arguments.checkOrOr(2, 3, args.length);

            final String input = args[0].asString();
            final String regex = args[1].asString();
            final int limit = (args.length == 3) ? args[2].asInt() : 0;

            final String[] parts = input.split(regex, limit);
            return LzrArray.of(parts);
        }
    }

    public static final class range implements Function {

        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            Arguments.checkRange(1, 3, args.length);

            final long from, to, step;
            switch (args.length) {
                default:
                case 1:
                    from = 0;
                    to = getLong(args[0]);
                    step = 1;
                    break;
                case 2:
                    from = getLong(args[0]);
                    to = getLong(args[1]);
                    step = 1;
                    break;
                case 3:
                    from = getLong(args[0]);
                    to = getLong(args[1]);
                    step = getLong(args[2]);
                    break;
            }
            return RangeValue.of(from, to, step);
        }

        private static long getLong(LzrValue v) {
            if (v.type() == Types.NUMBER) {
                return ((LzrNumber) v).asLong();
            }
            return v.asInt();
        }

        private static class RangeValue extends LzrArray {

            public static LzrArray of(long from, long to, long step) {
                boolean isInvalid = false;
                isInvalid = isInvalid || (step == 0);
                isInvalid = isInvalid || ((step > 0) && (from >= to));
                isInvalid = isInvalid || ((step < 0) && (to >= from));
                if (isInvalid) return new LzrArray(0);
                return new RangeValue(from, to, step);
            }

            private final long from, to, step;
            private final int size;

            public RangeValue(long from, long to, long step) {
                super(new LzrValue[0]);
                this.from = from;
                this.to = to;
                this.step = step;
                final long base = (from < to) ? (to - from) : (from - to);
                final long absStep = (step < 0) ? -step : step;
                this.size = (int) (base / absStep + (base % absStep == 0 ? 0 : 1));
            }

            @NotNull
            @Override
            public LzrValue[] getCopyElements() {
                final LzrValue[] result = new LzrValue[size];
                int i = 0;
                if (isIntegerRange()) {
                    final int toInt = (int) to;
                    final int stepInt = (int) step;
                    for (int value = (int) from; value < toInt; value += stepInt) {
                        result[i++] = LzrNumber.of(value);
                    }
                } else {
                    for (long value = from; value < to; value += step) {
                        result[i++] = LzrNumber.of(value);
                    }
                }
                return result;
            }

            private boolean isIntegerRange() {
                if (to > 0) {
                    return (from > Integer.MIN_VALUE && to < Integer.MAX_VALUE);
                }
                return (to > Integer.MIN_VALUE && from < Integer.MAX_VALUE);
            }

            @Override
            public int size() {
                return size;
            }

            @NotNull
            @Override
            public LzrValue get(int index) {
                if (isIntegerRange()) {
                    return LzrNumber.of((int) (from + index * step));
                }
                return LzrNumber.of(from + (long) index * step);
            }

            @Override
            public void set(int index, @NotNull LzrValue value) {
                // not implemented
            }

            @NotNull
            @Override
            public LzrValue[] raw() {
                return getCopyElements();
            }

            @NotNull
            @Override
            public String asString() {
                if (size == 0) return "[]";

                final StringBuilder sb = new StringBuilder();
                sb.append('[').append(from);
                for (long value = from + step; value < to; value += step) {
                    sb.append(", ").append(value);
                }
                sb.append(']');
                return sb.toString();
            }

            @NotNull
            @Override
            public Iterator<LzrValue> iterator() {
                if (isIntegerRange()) {
                    final int toInt = (int) to;
                    final int stepInt = (int) step;
                    return new Iterator<LzrValue>() {

                        int value = (int) from;

                        @Override
                        public boolean hasNext() {
                            return (stepInt > 0) ? (value < toInt) : (value > toInt);
                        }

                        @Override
                        public LzrValue next() {
                            final int result = value;
                            value += stepInt;
                            return LzrNumber.of(result);
                        }

                        @Override
                        public void remove() { }
                    };
                }

                return new Iterator<LzrValue>() {

                    long value = from;

                    @Override
                    public boolean hasNext() {
                        return (step > 0) ? (value < to) : (value > to);
                    }

                    @Override
                    public LzrValue next() {
                        final long result = value;
                        value += step;
                        return LzrNumber.of(result);
                    }

                    @Override
                    public void remove() { }
                };
            }

            @Override
            public int hashCode() {
                int hash = 5;
                hash = 59 * hash + Long.hashCode(this.from);
                hash = 59 * hash + Long.hashCode(this.to);
                hash = 59 * hash + Long.hashCode(this.step);
                return hash;
            }

            @Override
            public boolean equals(Object obj) {
                if (this == obj) return true;
                if (obj == null) return false;
                if (getClass() != obj.getClass())
                    return false;
                final RangeValue other = (RangeValue) obj;
                if (this.from != other.from) return false;
                if (this.to != other.to) return false;
                return this.step == other.step;
            }

            @Override
            public int compareTo(@NotNull LzrValue other) {
                if (other.type() == Types.ARRAY) {
                    final int lengthCompare = Integer.compare(size(), ((LzrArray) other).size());
                    if (lengthCompare != 0) return lengthCompare;

                    if (other instanceof RangeValue) {
                        int compareResult;
                        compareResult = Long.compare(this.from, ((RangeValue) other).from);
                        if (compareResult != 0) return compareResult;
                        compareResult = Long.compare(this.to, ((RangeValue) other).to);
                        if (compareResult != 0) return compareResult;
                    }
                }
                return asString().compareTo(other.asString());
            }

            @NotNull
            @Override
            public String toString() {
                if (step == 1) {
                    return String.format("range(%d, %d)", from, to);
                }
                return String.format("range(%d, %d, %d)", from, to, step);
            }
        }
    }

    public static final class reduce implements Function {

        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            Arguments.check(3, args.length);

            final LzrValue container = args[0];
            final LzrValue identity = args[1];
            final Function accumulator = ValueUtils.consumeFunction(args[2], 2);
            if (container.type() == Types.ARRAY) {
                LzrValue result = identity;
                final LzrArray array = (LzrArray) container;
                for (LzrValue element : array) {
                    result = accumulator.execute(result, element);
                }
                return result;
            }
            if (container.type() == Types.MAP) {
                LzrValue result = identity;
                final LzrMap map = (LzrMap) container;
                for (Map.Entry<LzrValue, LzrValue> element : map) {
                    result = accumulator.execute(result, element.getKey(), element.getValue());
                }
                return result;
            }
            throw new LzrException("TypeException", "Invalid first argument. Array or map expected");
        }
    }

    public static final class map implements Function {

        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            Arguments.checkOrOr(2, 3, args.length);

            final LzrValue container = args[0];
            if (container.type() == Types.ARRAY) {
                final Function mapper = ValueUtils.consumeFunction(args[1], 1);
                return mapArray((LzrArray) container, mapper);
            }

            if (container.type() == Types.MAP) {
                final Function keyMapper = ValueUtils.consumeFunction(args[1], 1);
                final Function valueMapper = ValueUtils.consumeFunction(args[2], 2);
                return mapMap((LzrMap) container, keyMapper, valueMapper);
            }

            throw new LzrException("TypeException ","Invalid first argument. Array or map expected");
        }

        @NotNull
        static LzrArray mapArray(@NotNull LzrArray array, @NotNull Function mapper) {
            final int size = array.size();
            return new LzrArray(size, index -> mapper.execute(array.get(index)));
        }

        static LzrMap mapMap(LzrMap map, Function keyMapper, Function valueMapper) {
            final LzrMap result = new LzrMap(map.size());
            for (Map.Entry<LzrValue, LzrValue> element : map) {
                final LzrValue newKey = keyMapper.execute(element.getKey());
                final LzrValue newValue = valueMapper.execute(element.getValue());
                result.set(newKey, newValue);
            }
            return result;
        }
    }
}
