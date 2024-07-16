package com.kingmang.lazurite.core

import com.kingmang.lazurite.runtime.values.LzrValue

fun interface Function {
    fun execute(vararg args: LzrValue): LzrValue
}
