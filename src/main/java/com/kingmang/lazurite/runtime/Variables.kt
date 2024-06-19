package com.kingmang.lazurite.runtime

import com.kingmang.lazurite.runtime.values.LzrNumber
import com.kingmang.lazurite.runtime.values.LzrValue
import lombok.NoArgsConstructor
import java.util.concurrent.ConcurrentHashMap
import kotlin.concurrent.Volatile

@NoArgsConstructor
object Variables {
    private val lock = Any()

    @Volatile
    private var scope: Scope? = null

    init {
        clear()
    }

    fun variables(): Map<String, LzrValue> {
        return scope!!.variables
    }

    @JvmStatic
    fun clear() {
        scope = Scope()
        scope!!.variables.clear()
        scope!!.variables["true"] = LzrNumber.ONE
        scope!!.variables["false"] = LzrNumber.ZERO
    }

    @JvmStatic
    fun push() {
        synchronized(lock) {
            scope = Scope(scope)
        }
    }

    @JvmStatic
    fun pop() {
        synchronized(lock) {
            if (scope!!.parent != null) {
                scope = scope!!.parent
            }
        }
    }

    @JvmStatic
    fun isExists(key: String): Boolean {
        synchronized(lock) {
            return findScope(key).isFound
        }
    }

    @JvmStatic
    fun get(key: String): LzrValue? {
        synchronized(lock) {
            val scopeData = findScope(key)
            if (scopeData.isFound) {
                return scopeData.scope!!.variables[key]
            }
        }
        return LzrNumber.ZERO
    }

    @JvmStatic
    fun set(key: String, value: LzrValue) {
        synchronized(lock) {
            findScope(key).scope!!.variables.put(key, value)
        }
    }

    @JvmStatic
    fun define(key: String, value: LzrValue) {
        synchronized(lock) {
            scope!!.variables.put(key, value)
        }
    }

    @JvmStatic
    fun remove(key: String) {
        synchronized(lock) {
            findScope(key).scope!!.variables.remove(key)
        }
    }


    private fun findScope(variable: String): ScopeFindData {
        val result = ScopeFindData()

        var current = scope
        do {
            if (current!!.variables.containsKey(variable)) {
                result.isFound = true
                result.scope = current
                return result
            }
        } while ((current!!.parent.also { current = it }) != null)

        result.isFound = false
        result.scope = scope
        return result
    }

    private class Scope @JvmOverloads constructor(val parent: Scope? = null) {
        val variables: MutableMap<String, LzrValue> = ConcurrentHashMap()
    }

    private class ScopeFindData {
        var isFound: Boolean = false
        var scope: Scope? = null
    }
}
