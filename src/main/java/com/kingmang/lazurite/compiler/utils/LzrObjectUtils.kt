package com.kingmang.lazurite.compiler.utils

import com.kingmang.lazurite.core.CallStack
import com.kingmang.lazurite.core.Function
import com.kingmang.lazurite.runtime.values.*
import ru.DmN.pht.jvm.utils.vtype.DynamicArguments
import ru.DmN.pht.jvm.utils.vtype.DynamicReturn
import ru.DmN.pht.utils.mapArray
import java.lang.reflect.Modifier

@Suppress("UNCHECKED_CAST")
object LzrObjectUtils {
    @JvmStatic
    @DynamicArguments
    fun arraySet(obj: Any?, index: Any?, value: Any?) {
        obj!!
        if (obj is Array<*>) {
            val type = obj.javaClass.componentType
            if (type.isPrimitive)
                throw UnsupportedOperationException() // todo
            else if (type.isAssignableFrom(LzrValue::class.java))
                (obj as Array<LzrValue>)[(index as Number).toInt()] = castToLzrValue(value)
            else (obj as Array<Any?>)[(index as Number).toInt()] = value
        } else invokeMethod(obj, "set", index, value)
    }

    @JvmStatic
    @DynamicReturn
    @DynamicArguments
    fun arrayGet(obj: Any?, index: Any?): Any? {
        obj!!
        return if (obj is Array<*>) {
            val type = obj.javaClass.componentType
            if (type.isPrimitive)
                throw UnsupportedOperationException() // todo
            else if (type.isAssignableFrom(LzrValue::class.java))
                (obj as Array<LzrValue>)[(index as Number).toInt()].raw()
            else (obj as Array<Any?>)[(index as Number).toInt()]
        } else invokeMethod(obj, "get", index)
    }

    @JvmStatic
    @DynamicReturn
    fun invokeLambdaRt(obj: Any?, name: String, vararg args: Any?): Any? =
        when (obj) {
            null -> throw NullPointerException()
            is Function -> {
                CallStack.enter(name, obj, null)
                obj.execute(*castToLzrValue(args as Array<Any?>)).run {
                    CallStack.exit()
                    this.raw()
                }
            }

            else -> obj.javaClass.declaredMethods
                .asSequence()
                .drop(Any::class.java.declaredMethods.size)
                .find { it.parameterCount == args.size }!!
                .invoke(obj, *args)
        }

    @JvmStatic
    @DynamicReturn
    fun invokeLambda(obj: Any?, vararg args: Any?): Any? =
        when (obj) {
            null -> throw NullPointerException()
            is Function -> obj.execute(*castToLzrValue(args as Array<Any?>)).raw()
            else -> obj.javaClass.declaredMethods
                .asSequence()
                .drop(Any::class.java.declaredMethods.size)
                .find { it.parameterCount == args.size }!!
                .invoke(obj, *args)
        }

    @JvmStatic
    @DynamicReturn
    fun invokeMethod(obj: Any?, name: String, vararg args: Any?): Any? =
        when (obj) {
            null -> throw NullPointerException()
            is LzrMetaObject -> obj.`lzr$invokeMethod`(name, *args)
            is Map<*, *> -> (obj[LzrString(name)] as LzrFunction).value.execute(*castToLzrValue(args as Array<Any?>)).raw()
            else -> obj.javaClass.declaredMethods.find { it.name == name && it.parameterCount == args.count() }!!.run {
                isAccessible = true
                invoke(obj, *args)
            }
        }

    @JvmStatic
    fun setField(obj: Any?, name: String, value: Any?) {
        when (obj) {
            null -> throw NullPointerException()
            is LzrMetaObject -> obj.`lzr$setField`(name, value)
            else -> {
                val static = obj is Class<*>
                val clazz = if (static) obj as Class<*> else obj.javaClass
                clazz.declaredFields.find { it.name == name && Modifier.isStatic(it.modifiers) == static }?.let {
                    it.isAccessible = true
                    it.set(if (static) null else obj, value)
                }
            }
        }
    }

    @JvmStatic
    @DynamicReturn
    fun getField(obj: Any?, name: String): Any? {
        if (obj == null)
            throw NullPointerException()
        return if (obj is LzrMetaObject)
            obj.`lzr$getField`(name)
        else {
            val static = obj is Class<*>
            val clazz = if (static) obj as Class<*> else obj.javaClass
            clazz.declaredFields.find { it.name == name && Modifier.isStatic(it.modifiers) == static }?.let {
                it.isAccessible = true
                return it.get(if (static) null else obj)
            }
            clazz.declaredMethods.find { it.name == name && Modifier.isStatic(it.modifiers) == static }?.let {
                it.isAccessible = true
                return LzrWrappedFunction(if (static) null else obj, it)
            }
        }
    }

    @JvmStatic
    fun castToLzrValue(args: Array<Any?>): Array<LzrValue> =
        Array(args.size) { castToLzrValue(args[it]) }

    @JvmStatic
    fun castToLzrValue(it: Any?): LzrValue =
        when (it) {
            null -> LzrNull
            is Number    -> LzrNumber.of(it)
            is String    -> LzrString(it)
            is Function  -> LzrFunction(it)
            is Array<*>  -> LzrArray(it.mapArray { castToLzrValue(it) })
            is Map<*, *> -> LzrMap(it.size).apply { it.forEach { k, v -> this[castToLzrValue(k)] = castToLzrValue(v) } }
            is LzrValue  -> it
            else         -> LzrReference(it)
        }
}