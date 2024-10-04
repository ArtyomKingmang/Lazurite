package com.kingmang.lazurite.runtime

import com.kingmang.lazurite.runtime.scope.Scope
import com.kingmang.lazurite.runtime.scope.findOrCurrent
import com.kingmang.lazurite.runtime.values.LzrNumber
import com.kingmang.lazurite.runtime.values.LzrValue
import kotlin.concurrent.Volatile

private typealias VariableMap = MutableMap<String, LzrValue>

object Variables {
    @Volatile
    private var scope = createRootScope()

    @JvmStatic
    fun variables(): Map<String, LzrValue> {
        return scope.data
    }

    @JvmStatic
    @Synchronized
    fun clear() {
        scope = createRootScope()
    }

    @JvmStatic
    @Synchronized
    fun push() {
        scope = createChildScope(scope)
    }

    @JvmStatic
    @Synchronized
    fun pop() {
        scope.parent?.also { parent ->
            scope = parent
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
            return scopeData.scope.data[key]
        }
        return LzrNumber.ZERO
    }

    @JvmStatic
    @Synchronized
    fun set(key: String, value: LzrValue) {
        findScope(key).scope.data[key] = value
    }

    @JvmStatic
    @Synchronized
    fun define(key: String, value: LzrValue) {
        scope.data[key] = value
    }

    @JvmStatic
    @Synchronized
    fun remove(key: String) {
        findScope(key).scope.data.remove(key)
    }

    fun findScope(variable: String) = scope.findOrCurrent {
        it.data.containsKey(variable)
    }

    private fun createRootScope() =
        Scope<VariableMap>(null, HashMap()).apply {
            data["true"] = LzrNumber.ONE
            data["false"] = LzrNumber.ZERO
        }

    private fun createChildScope(parent: Scope<VariableMap>) =
        Scope(parent, HashMap())
}
