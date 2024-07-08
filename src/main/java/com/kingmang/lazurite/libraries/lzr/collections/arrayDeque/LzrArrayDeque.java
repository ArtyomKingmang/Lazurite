package com.kingmang.lazurite.libraries.lzr.collections.arrayDeque;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.runtime.values.LzrArray;
import com.kingmang.lazurite.runtime.values.LzrNumber;
import com.kingmang.lazurite.runtime.values.LzrValue;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public final class LzrArrayDeque {
        static Deque<LzrValue> queue;

        public static LzrValue newArrayDeque(LzrValue[] args){
            queue = new ArrayDeque<>();
            return LzrNumber.ZERO;
        }
        public static LzrValue addToQueue(LzrValue[] args) {
            Arguments.check(1,  args.length);
            queue.add(args[0]);
            return LzrNumber.ZERO;

        }

        public static LzrValue toArray(LzrValue[] args) {
            List<LzrValue> array_list = new ArrayList<>(queue);
            return new LzrArray(array_list);

        }

        public static LzrValue remove(LzrValue[] args) {
            Arguments.check(1,  args.length);
            queue.remove(args[0]);
            return LzrNumber.ZERO;
        }

        public static LzrValue sizeQueue(LzrValue[] args) {
            Arguments.check(1,  args.length);
            return new LzrNumber(queue.size());
        }



    }