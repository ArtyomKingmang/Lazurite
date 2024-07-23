package com.kingmang.lazurite.memory;

import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.runtime.values.LzrValue;

public class Storage {

    private static long left = 2080000000;

    public static void free(LzrValue obj) {
        left += StorageUtils.size(obj);
    }

    public static void segment(LzrValue obj) {
        left -= StorageUtils.size(obj);
        if (left <= 0) throw new LzrException("SegmentationFault", "segmentation fault, last allocation: 'allocation " + obj + ":" + StorageUtils.size(obj) + "'");
    }

    public static void segment(int obj) {
        left -= obj;
    }

    public static void reset() { left = 2080000000; }

    public static long left() {
        return left;
    }

}
