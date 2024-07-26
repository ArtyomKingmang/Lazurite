package com.kingmang.lazurite.core

import com.kingmang.lazurite.runtime.values.LzrValue

fun interface Instantiable {
    fun newInstance(args: Array<LzrValue>): LzrValue
}
