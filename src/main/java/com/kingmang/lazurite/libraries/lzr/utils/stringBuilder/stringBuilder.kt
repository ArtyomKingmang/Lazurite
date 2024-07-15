package com.kingmang.lazurite.libraries.lzr.utils.stringBuilder

import com.kingmang.lazurite.libraries.Library
import com.kingmang.lazurite.runtime.Variables.define
import com.kingmang.lazurite.runtime.values.LzrMap

@Suppress("unused", "ClassName")
class stringBuilder : Library {
    override fun init() {
        val stringBuilder = LzrMap(5)
        stringBuilder["append"] = StringBuilderMethods::addToBuilder
        stringBuilder["new"] = StringBuilderMethods::newBuilder
        stringBuilder["delete"] = StringBuilderMethods::deleteBuilder
        stringBuilder["toStr"] = StringBuilderMethods::toStrBuilder
        stringBuilder["deleteCharAt"] = StringBuilderMethods::deleteCharAtBuilder
        define("stringBuilder", stringBuilder)
    }
}
