package com.kingmang.lazurite.runtime

import lombok.NoArgsConstructor

@NoArgsConstructor
object Libraries {
    @Volatile
    private var scope: Scope? = null

    init {
        clear()
    }

    @JvmStatic
    @Synchronized
    fun clear() {
        scope = Scope()
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
    fun isExists(path: String): Boolean {
        return findScope(path).isFound
    }

    @JvmStatic
    @Synchronized
    fun add(path: String) {
        scope!!.libraries.add(path)
    }

    @JvmStatic
    @Synchronized
    fun remove(path: String) {
        findScope(path).scope!!.libraries.remove(path)
    }

    private fun findScope(path: String): ScopeFindData {
        val result = ScopeFindData()

        var current = scope
        do {
            if (current!!.libraries.contains(path)) {
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
        val libraries: MutableList<String> = ArrayList()
    }

    private class ScopeFindData {
        var isFound: Boolean = false
        var scope: Scope? = null
    }
}