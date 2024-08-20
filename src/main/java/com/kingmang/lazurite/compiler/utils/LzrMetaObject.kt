package com.kingmang.lazurite.compiler.utils

import com.kingmang.lazurite.core.Function
import ru.DmN.pht.jvm.utils.vtype.DynamicReturn
import com.kingmang.lazurite.compiler.utils.LzrObjectUtils.castToLzrValue

open class LzrMetaObject {
    private val `lzr$fields`: MutableMap<String, Any?> = HashMap()

    fun `lzr$setField`(name: String, value: Any?) {
        val fld = this.javaClass.fields.find { it.name == name }
        if (fld == null)
            `lzr$fields`[name] = value
        else fld.set(this, value)
    }

    @DynamicReturn
    fun `lzr$getField`(name: String): Any? {
        val fld = this.javaClass.declaredFields.find { it.name == name }
        return if (fld == null)
            `lzr$fields`[name] ?: this.javaClass.declaredMethods.find { it.name == name }?.let { LzrWrappedFunction(this, it) }
        else fld.run {
            isAccessible = true
            get(this@LzrMetaObject)
        }
    }

    @DynamicReturn
    fun `lzr$invokeMethod`(name: String, vararg args: Any?): Any? {
        val method = this.javaClass.declaredMethods.find { it.name == name && it.parameterCount == args.size }
        return if (method == null)
            (`lzr$fields`[name] as Function).execute(*castToLzrValue(args as Array<Any?>))
        else method.run {
            isAccessible = true
            invoke(this@LzrMetaObject, *args)
        }
    }
}