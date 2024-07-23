package com.kingmang.lazurite.memory

import com.kingmang.lazurite.runtime.values.*

object StorageUtils {
    @JvmStatic
    fun size(value: LzrValue): Short {
        if (value is LzrNumber) {
            return 12
        } else if (value is LzrString) {
            return 24
        } else if (value is Allocation) {
            return value.getAllocated().toShort()
        } else if (value is LzrArray) {
            return 24
        } else if (value is LzrFunction) {
            return 48
        } else if (value is LzrReference) {
            return 128
        }
        return 24
    }
}
