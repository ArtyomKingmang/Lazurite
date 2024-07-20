package com.kingmang.lazurite.runtime

import com.kingmang.lazurite.runtime.scope.Scope
import com.kingmang.lazurite.runtime.scope.findOrCurrent

private typealias LibraryList = MutableList<String>

object Libraries {
    @Volatile
    private var scope = createRootScope()

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
    @Suppress("unused")
    fun pop() {
        scope.parent?.let { scope = it }
    }

    @JvmStatic
    @Synchronized
    fun isExists(path: String): Boolean =
        findScope(path).isFound

    @JvmStatic
    @Synchronized
    fun add(path: String) {
        scope.data.add(path)
    }

    @JvmStatic
    @Synchronized
    fun remove(path: String) {
        findScope(path).scope.data.remove(path)
    }

    private fun findScope(path: String) =
        scope.findOrCurrent { it.data.contains(path) }

    private fun createRootScope() =
        Scope<LibraryList>(null, ArrayList())

    private fun createChildScope(parent: Scope<LibraryList>) =
        Scope(parent, ArrayList())
}