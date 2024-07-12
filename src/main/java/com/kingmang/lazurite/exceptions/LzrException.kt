package com.kingmang.lazurite.exceptions

open class LzrException(
    val type: String,
    val text: String
) : RuntimeException()