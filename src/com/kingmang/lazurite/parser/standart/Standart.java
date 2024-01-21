package com.kingmang.lazurite.parser.standart;

import com.kingmang.lazurite.exceptions.LZRException;
import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.core.Types;
import com.kingmang.lazurite.utils.ValueUtils;
import com.kingmang.lazurite.console.Console;
import com.kingmang.lazurite.runtime.LZR.*;
import com.kingmang.lazurite.runtime.UserDefinedFunction;
import com.kingmang.lazurite.runtime.Value;

import java.io.UnsupportedEncodingException;
import java.util.*;

public class Standart {

    public static final class echo implements Function {

        @Override
        public Value execute(Value... args) {
            final StringBuilder sb = new StringBuilder();
            for (Value arg : args) {
                sb.append(arg.asString());
                sb.append(" ");
            }
            Console.println(sb.toString());
            return LZRNumber.ZERO;
        }
    }

    public static final class input implements Function {
        @Override
        public Value execute(Value... args) {
            Scanner sc = new Scanner(System.in);
            return new LZRString(sc.nextLine());
        }
    }

    public static final class equal implements Function {
        @Override
        public Value execute(Value... args) {
           boolean check = args[0].equals(args[1]);
           return LZRNumber.fromBoolean(check);
        }
    }
    public static final class Array implements Function {

        @Override
        public Value execute(Value... args) {
            return createArray(args, 0);
        }

        private LZRArray createArray(Value[] args, int index) {
            final int size = args[index].asInt();
            final int last = args.length - 1;
            LZRArray array = new LZRArray(size);
            if (index == last) {
                for (int i = 0; i < size; i++) {
                    array.set(i, LZRNumber.ZERO);
                }
            } else if (index < last) {
                for (int i = 0; i < size; i++) {
                    array.set(i, createArray(args, index + 1));
                }
            }
            return array;
        }
    }
    public static final class string {

        private string() { }

        public static LZRArray getBytes(Value[] args) {
            Arguments.checkOrOr(1, 2, args.length);
            final String charset = (args.length == 2) ? args[1].asString() : "UTF-8";
            try {
                return LZRArray.of(args[0].asString().getBytes(charset));
            } catch (UnsupportedEncodingException uee) {
                throw new RuntimeException(uee);
            }
        }

    }

    public static final class length implements Function {

        @Override
        public Value execute(Value... args) {
            Arguments.check(1, args.length);

            final Value val = args[0];
            final int length;
            switch (val.type()) {
                case Types.ARRAY:
                    length = ((LZRArray) val).size();
                    break;
                case Types.MAP:
                    length = ((LZRMap) val).size();
                    break;
                case Types.STRING:
                    length = ((LZRString) val).length();
                    break;
                case Types.FUNCTION:
                    final Function func = ((LZRFunction) val).getValue();
                    if (func instanceof UserDefinedFunction) {
                        length = ((UserDefinedFunction) func).getArgsCount();
                    } else {
                        length = 0;
                    }
                    break;
                default:
                    length = 0;

            }
            return LZRNumber.of(length);
        }
    }

    public static final class sprintf implements Function {

        @Override
        public Value execute(Value... args) {
            Arguments.checkAtLeast(1, args.length);

            final String format = args[0].asString();
            final Object[] values = new Object[args.length - 1];
            for (int i = 1; i < args.length; i++) {
                values[i - 1] = (args[i].type() == Types.NUMBER)
                        ? args[i].raw()
                        : args[i].asString();
            }
            return new LZRString(String.format(format, values));
        }
    }

    public static final class substr implements Function {

        @Override
        public Value execute(Value... args) {
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

            return new LZRString(result);
        }
    }

    public static final class parse {
        private parse() { }

        public static Value parseInt(Value[] args) {
            Arguments.checkOrOr(1, 2, args.length);
            final int radix = (args.length == 2) ? args[1].asInt() : 10;
            return LZRNumber.of(Integer.parseInt(args[0].asString(), radix));
        }
        public static Value parseLong(Value[] args) {
            Arguments.checkOrOr(1, 2, args.length);
            final int radix = (args.length == 2) ? args[1].asInt() : 10;
            return LZRNumber.of(Long.parseLong(args[0].asString(), radix));
        }
    }

    public static final class foreach implements Function {

        private static final int UNKNOWN = -1;

        @Override
        public Value execute(Value... args) {
            Arguments.check(2, args.length);
            final Value container = args[0];
            final Function consumer = ValueUtils.consumeFunction(args[1], 1);
            final int argsCount;
            if (consumer instanceof UserDefinedFunction) {
                argsCount = ((UserDefinedFunction) consumer).getArgsCount();
            } else {
                argsCount = UNKNOWN;
            }

            switch (container.type()) {
                case Types.STRING:
                    final LZRString string = (LZRString) container;
                    if (argsCount == 2) {
                        for (char ch : string.asString().toCharArray()) {
                            consumer.execute(new LZRString(String.valueOf(ch)), LZRNumber.of(ch));
                        }
                    } else {
                        for (char ch : string.asString().toCharArray()) {
                            consumer.execute(new LZRString(String.valueOf(ch)));
                        }
                    }
                    return string;

                case Types.ARRAY:
                    final LZRArray array = (LZRArray) container;
                    if (argsCount == 2) {
                        int index = 0;
                        for (Value element : array) {
                            consumer.execute(element, LZRNumber.of(index++));
                        }
                    } else {
                        for (Value element : array) {
                            consumer.execute(element);
                        }
                    }
                    return array;

                case Types.MAP:
                    final LZRMap map = (LZRMap) container;
                    for (Map.Entry<Value, Value> element : map) {
                        consumer.execute(element.getKey(), element.getValue());
                    }
                    return map;

                default:
                    throw new LZRException("TypeExeption ","Cannot iterate " + Types.typeToString(container.type()));
            }
        }
    }


    public static final class filter implements Function {

        private final boolean takeWhile;

        public filter(boolean takeWhile) {
            this.takeWhile = takeWhile;
        }

        @Override
        public Value execute(Value... args) {
            Arguments.check(2, args.length);
            final Value container = args[0];
            final Function predicate = ValueUtils.consumeFunction(args[1], 1);
            if (container.type() == Types.ARRAY) {
                return filterArray((LZRArray) container, predicate, takeWhile);
            }

            if (container.type() == Types.MAP) {
                return filterMap((LZRMap) container, predicate, takeWhile);
            }

            throw new LZRException("TypeExeption", "Invalid first argument. Array or map expected");
        }

        private Value filterArray(LZRArray array, Function predicate, boolean takeWhile) {
            final int size = array.size();
            final List<Value> values = new ArrayList<>(size);
            for (Value value : array) {
                if (predicate.execute(value) != LZRNumber.ZERO) {
                    values.add(value);
                } else if (takeWhile) break;
            }
            final int newSize = values.size();
            return new LZRArray(values.toArray(new Value[newSize]));
        }

        private Value filterMap(LZRMap map, Function predicate, boolean takeWhile) {
            final LZRMap result = new LZRMap(map.size());
            for (Map.Entry<Value, Value> element : map) {
                if (predicate.execute(element.getKey(), element.getValue()) != LZRNumber.ZERO) {
                    result.set(element.getKey(), element.getValue());
                } else if (takeWhile) break;
            }
            return result;
        }
    }

    public static final class split implements Function {

        @Override
        public Value execute(Value... args) {
            Arguments.checkOrOr(2, 3, args.length);

            final String input = args[0].asString();
            final String regex = args[1].asString();
            final int limit = (args.length == 3) ? args[2].asInt() : 0;

            final String[] parts = input.split(regex, limit);
            return LZRArray.of(parts);
        }
    }

    public static final class range implements Function {

        @Override
        public Value execute(Value... args) {
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

        private static long getLong(Value v) {
            if (v.type() == Types.NUMBER) {
                return ((LZRNumber) v).asLong();
            }
            return v.asInt();
        }

        private static class RangeValue extends LZRArray {

            public static LZRArray of(long from, long to, long step) {
                boolean isInvalid = false;
                isInvalid = isInvalid || (step == 0);
                isInvalid = isInvalid || ((step > 0) && (from >= to));
                isInvalid = isInvalid || ((step < 0) && (to >= from));
                if (isInvalid) return new LZRArray(0);
                return new RangeValue(from, to, step);
            }

            private final long from, to, step;
            private final int size;

            public RangeValue(long from, long to, long step) {
                super(new Value[0]);
                this.from = from;
                this.to = to;
                this.step = step;
                final long base = (from < to) ? (to - from) : (from - to);
                final long absStep = (step < 0) ? -step : step;
                this.size = (int) (base / absStep + (base % absStep == 0 ? 0 : 1));
            }

            @Override
            public Value[] getCopyElements() {
                final Value[] result = new Value[size];
                int i = 0;
                if (isIntegerRange()) {
                    final int toInt = (int) to;
                    final int stepInt = (int) step;
                    for (int value = (int) from; value < toInt; value += stepInt) {
                        result[i++] = LZRNumber.of(value);
                    }
                } else {
                    for (long value = from; value < to; value += step) {
                        result[i++] = LZRNumber.of(value);
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

            @Override
            public Value get(int index) {
                if (isIntegerRange()) {
                    return LZRNumber.of((int) (from + index * step));
                }
                return LZRNumber.of(from + (long) index * step);
            }

            @Override
            public void set(int index, Value value) {
                // not implemented
            }

            @Override
            public Object raw() {
                return getCopyElements();
            }

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

            @Override
            public Iterator<Value> iterator() {
                if (isIntegerRange()) {
                    final int toInt = (int) to;
                    final int stepInt = (int) step;
                    return new Iterator<Value>() {

                        int value = (int) from;

                        @Override
                        public boolean hasNext() {
                            return (stepInt > 0) ? (value < toInt) : (value > toInt);
                        }

                        @Override
                        public Value next() {
                            final int result = value;
                            value += stepInt;
                            return LZRNumber.of(result);
                        }

                        @Override
                        public void remove() { }
                    };
                }
                return new Iterator<Value>() {

                    long value = from;

                    @Override
                    public boolean hasNext() {
                        return (step > 0) ? (value < to) : (value > to);
                    }

                    @Override
                    public Value next() {
                        final long result = value;
                        value += step;
                        return LZRNumber.of(result);
                    }

                    @Override
                    public void remove() { }
                };
            }

            @Override
            public int hashCode() {
                int hash = 5;
                hash = 59 * hash + (int) (this.from ^ (this.from >>> 32));
                hash = 59 * hash + (int) (this.to ^ (this.to >>> 32));
                hash = 59 * hash + (int) (this.step ^ (this.step >>> 32));
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
                if (this.step != other.step) return false;
                return true;
            }

            @Override
            public int compareTo(Value o) {
                if (o.type() == Types.ARRAY) {
                    final int lengthCompare = Integer.compare(size(), ((LZRArray) o).size());
                    if (lengthCompare != 0) return lengthCompare;

                    if (o instanceof RangeValue) {
                        final RangeValue o2 = ((RangeValue) o);
                        int compareResult;
                        compareResult = Long.compare(this.from, o2.from);
                        if (compareResult != 0) return compareResult;
                        compareResult = Long.compare(this.to, o2.to);
                        if (compareResult != 0) return compareResult;
                    }
                }
                return asString().compareTo(o.asString());
            }

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
        public Value execute(Value... args) {
            Arguments.check(3, args.length);

            final Value container = args[0];
            final Value identity = args[1];
            final Function accumulator = ValueUtils.consumeFunction(args[2], 2);
            if (container.type() == Types.ARRAY) {
                Value result = identity;
                final LZRArray array = (LZRArray) container;
                for (Value element : array) {
                    result = accumulator.execute(result, element);
                }
                return result;
            }
            if (container.type() == Types.MAP) {
                Value result = identity;
                final LZRMap map = (LZRMap) container;
                for (Map.Entry<Value, Value> element : map) {
                    result = accumulator.execute(result, element.getKey(), element.getValue());
                }
                return result;
            }
            throw new LZRException("TypeExeption", "Invalid first argument. Array or map expected");
        }
    }
}
