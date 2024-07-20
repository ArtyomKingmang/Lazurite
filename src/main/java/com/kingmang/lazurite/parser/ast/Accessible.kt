package com.kingmang.lazurite.parser.ast

import com.kingmang.lazurite.runtime.values.LzrValue

interface Accessible : Node {
    fun get(): LzrValue
    fun set(value: LzrValue): LzrValue
}
