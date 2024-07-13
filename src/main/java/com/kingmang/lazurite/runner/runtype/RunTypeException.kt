package com.kingmang.lazurite.runner.runtype

class RunTypeException(
    message: String,
    cause: Throwable? = null
) : IllegalArgumentException(message, cause)