package com.kingmang.lazurite.runtime.scope

inline fun <T> Scope<T>.findOrCurrent(condition: (Scope<T>) -> Boolean): ScopeFindData<T> {
    var current = this

    do {
        if (condition.invoke(current)) {
            return ScopeFindData(current, true)
        }
    } while ((current.parent?.also { current = it }) != null)

    return ScopeFindData(this, false)
}