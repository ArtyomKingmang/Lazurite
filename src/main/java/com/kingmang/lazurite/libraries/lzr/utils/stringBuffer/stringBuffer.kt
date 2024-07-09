package com.kingmang.lazurite.libraries.lzr.utils.stringBuffer

import com.kingmang.lazurite.libraries.Library
import com.kingmang.lazurite.runtime.Variables.define
import com.kingmang.lazurite.runtime.values.LzrMap
import com.kingmang.lazurite.runtime.values.LzrValue

@Suppress("unused", "ClassName")
class stringBuffer : Library {
    override fun init() {
        val stringBuffer = LzrMap(5)
        stringBuffer["append"] = { args: Array<LzrValue?>? -> StringBufferMethods.addToBuffer(args) }
        stringBuffer["new"] = { args: Array<LzrValue?>? -> StringBufferMethods.newBuffer(args) }
        stringBuffer["delete"] = { args: Array<LzrValue?>? -> StringBufferMethods.deleteBuffer(args) }
        stringBuffer["toStr"] = { args: Array<LzrValue?>? -> StringBufferMethods.toStrBuffer(args) }
        stringBuffer["deleteCharAt"] = { args: Array<LzrValue?>? -> StringBufferMethods.deleteCharAtBuffer(args) }
        define("stringBuffer", stringBuffer)
    }
}
