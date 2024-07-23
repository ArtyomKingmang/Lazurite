package com.kingmang.lazurite.memory

import com.kingmang.lazurite.memory.Storage.segment
import com.kingmang.lazurite.memory.StorageUtils.size
import com.kingmang.lazurite.runtime.values.LzrArray
import com.kingmang.lazurite.runtime.values.LzrValue
import lombok.Getter
import java.util.*
import java.util.List

class Allocation : LzrValue {
    var list: LinkedList<LzrValue>

    private var allocated: Int
    private var defaultAlloc: Int

    fun getAllocated() : Int{
        return allocated
    }

    fun getDefaultAlloc() : Int{
        return defaultAlloc
    }

    constructor(list: LinkedList<LzrValue>, allocated: Int) {
        this.list = list
        segment(allocated)
        this.allocated = allocated
        this.defaultAlloc = allocated
    }

    constructor(allocated: Int, vararg values: LzrValue?) {
        this.list = LinkedList(List.of(*values))
        this.allocated = allocated
        this.defaultAlloc = allocated
    }

    constructor(size: Int) {
        this.list = LinkedList()
        this.allocated = size
        this.defaultAlloc = size
    }

    fun toList(): LzrValue {
        return LzrArray(list)
    }

    fun segment(obj: LzrValue?) {
        allocated -= size(obj!!).toInt()
    }

    fun clear() {
        allocated = defaultAlloc
        list.clear()
    }

    fun setAllocated(allocated: Int) {
        this.allocated = allocated
    }

    fun setDefaultAlloc(defaultAlloc: Int) {
        this.defaultAlloc = defaultAlloc
    }

    override fun toString(): String {
        return "allocation " + hashCode()
    }


    override fun raw(): Any? {
        return list
    }

    override fun asInt(): Int {
        return 0
    }

    override fun asNumber(): Double {
        return 0.0
    }

    override fun asString(): String {
        return ""
    }

    override fun asArray(): IntArray {
        return IntArray(0)
    }

    override fun type(): Int {
        return 0
    }

    override fun compareTo(o: LzrValue): Int {
        return 0
    }
}
