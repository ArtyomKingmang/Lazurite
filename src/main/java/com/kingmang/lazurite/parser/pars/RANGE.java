package com.kingmang.lazurite.parser.pars;

import com.kingmang.lazurite.core.*;
import com.kingmang.lazurite.runtime.LZR.LZRArray;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
import com.kingmang.lazurite.runtime.Value;


import java.util.Iterator;

public final class RANGE implements Function {

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