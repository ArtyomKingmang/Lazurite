package com.kingmang.lazurite.core

import com.kingmang.lazurite.exceptions.LzrException
import com.kingmang.lazurite.runtime.ClassInstanceValue
import lombok.Getter
import lombok.NoArgsConstructor

object Classes {
    private val classes: MutableMap<String, ClassInstanceValue> = HashMap()

    @Synchronized
    fun clear() =
        classes.clear()

    @Synchronized
    fun isExists(key: String): Boolean =
        classes.containsKey(key)

    @Synchronized
    operator fun set(key: String, classDef: ClassInstanceValue) {
        classes[key] = classDef
    }

    @Synchronized
    operator fun get(key: String): ClassInstanceValue? {
        if (!isExists(key))
            throw LzrException("UnknownClassException ", "Unknown class $key")
        return classes[key]
    }
}
