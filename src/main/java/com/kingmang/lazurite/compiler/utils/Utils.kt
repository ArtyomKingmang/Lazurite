package com.kingmang.lazurite.compiler.utils

inline fun <T, R> List<T>.mapMutable(skip: Int, transform: (T) -> R): MutableList<R> {
    val list = ArrayList<R>(this.size)
    for (i in skip until this.size)
        list.add(transform(this[i]))
    return list
}