package com.kingmang.lazurite.memory;

import com.kingmang.lazurite.runtime.values.*;

public class StorageUtils {
    public static short size(LzrValue value) {
        if (value instanceof LzrNumber) {
            return 12;
        } else if (value instanceof LzrString) {
            return 24;
        } else if (value instanceof Allocation p) {
            return (short) p.getAllocated();
        } else if (value instanceof LzrArray l) {
            return 24;
        } else if (value instanceof LzrFunction) {
            return 48;
        }
        return 24;
    }
}
