package com.kingmang.lazurite.runtime.scope

class Scope<T>(
    val parent: Scope<T>?,
    val data: T
)