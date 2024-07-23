package com.kingmang.lazurite.memory

import com.kingmang.lazurite.exceptions.LzrException
import com.kingmang.lazurite.memory.StorageUtils.size
import com.kingmang.lazurite.runtime.values.LzrValue

object Storage {
    private var left: Long = 2080000000

    fun free(obj: LzrValue?) {
        left += size(obj!!).toLong()
    }

    fun segment(obj: LzrValue) {
        left -= size(obj).toLong()
        if (left <= 0) throw LzrException(
            "SegmentationFault",
            "segmentation fault, last allocation: 'allocation " + obj + ":" + size(obj) + "'"
        )
    }

    @JvmStatic
    fun segment(obj: Int) {
        left -= obj.toLong()
    }

    fun reset() {
        left = 2080000000
    }

    fun left(): Long {
        return left
    }
}
