package com.kingmang.lazurite.libraries.lzr.utils.stringBuilder

import com.kingmang.lazurite.libraries.Library
import com.kingmang.lazurite.runtime.Variables.define
import com.kingmang.lazurite.runtime.values.LzrMap
import com.kingmang.lazurite.runtime.values.LzrValue

@Suppress("unused", "ClassName")
class stringBuilder : Library {
    override fun init() {
        val stringBuilder = LzrMap(5)
        stringBuilder["append"] = { args: Array<LzrValue?>? -> StringBuilderMethods.addToBuilder(args) }
        stringBuilder["new"] = { args: Array<LzrValue?>? -> StringBuilderMethods.newBuilder(args) }
        stringBuilder["delete"] = { args: Array<LzrValue?>? -> StringBuilderMethods.deleteBuilder(args) }
        stringBuilder["toStr"] = { args: Array<LzrValue?>? -> StringBuilderMethods.toStrBuilder(args) }
        stringBuilder["deleteCharAt"] = { args: Array<LzrValue?>? -> StringBuilderMethods.deleteCharAtBuilder(args) }
        define("stringBuilder", stringBuilder)
    }
}
