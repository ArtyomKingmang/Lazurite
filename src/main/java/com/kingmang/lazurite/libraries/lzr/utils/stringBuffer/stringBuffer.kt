package com.kingmang.lazurite.libraries.lzr.utils.stringBuffer

import com.kingmang.lazurite.libraries.Library
import com.kingmang.lazurite.runtime.Variables.define
import com.kingmang.lazurite.runtime.values.LzrMap

@Suppress("unused", "ClassName")
class stringBuffer : Library {
    override fun init() {
        val stringBuffer = LzrMap(5)
        stringBuffer["append"] = StringBufferMethods::addToBuffer
        stringBuffer["new"] = StringBufferMethods::newBuffer
        stringBuffer["delete"] = StringBufferMethods::deleteBuffer
        stringBuffer["toStr"] = StringBufferMethods::toStrBuffer
        stringBuffer["deleteCharAt"] = StringBufferMethods::deleteCharAtBuffer
        define("stringBuffer", stringBuffer)
    }
}
