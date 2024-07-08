package com.kingmang.lazurite.runtime

import com.kingmang.lazurite.runtime.values.LzrNumber
import com.kingmang.lazurite.runtime.values.LzrValue
import lombok.NoArgsConstructor
import kotlin.concurrent.Volatile

@NoArgsConstructor
object Variables {
    @Volatile
    private var scope: Scope? = null

    init {
        clear()
    }

    fun variables(): Map<String, LzrValue> {
        return scope!!.variables
    }

    @JvmStatic
    @Synchronized
    fun clear() {
        scope = Scope()
        scope!!.variables["true"] = LzrNumber.ONE
        scope!!.variables["false"] = LzrNumber.ZERO
    }

    @JvmStatic
    @Synchronized
    fun push() {
        scope = Scope(scope)
    }

    @JvmStatic
    @Synchronized
    fun pop() {
        if (scope!!.parent != null) {
            scope = scope!!.parent
        }
    }

    @JvmStatic
    @Synchronized
    fun isExists(key: String): Boolean {
        return findScope(key).isFound
    }

    @JvmStatic
    @Synchronized
    fun get(key: String): LzrValue? {
        val scopeData = findScope(key)
        if (scopeData.isFound) {
            return scopeData.scope!!.variables[key]
        }
        return LzrNumber.ZERO
    }

    @JvmStatic
    @Synchronized
    fun set(key: String, value: LzrValue) {
        findScope(key).scope!!.variables[key] = value
    }

    @JvmStatic
    @Synchronized
    fun define(key: String, value: LzrValue) {
        scope!!.variables[key] = value
    }

    @JvmStatic
    @Synchronized
    fun remove(key: String) {
        findScope(key).scope!!.variables.remove(key)
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
        val variables: MutableMap<String, LzrValue> = HashMap()
    }

    private class ScopeFindData {
        var isFound: Boolean = false
        var scope: Scope? = null
    }
}
