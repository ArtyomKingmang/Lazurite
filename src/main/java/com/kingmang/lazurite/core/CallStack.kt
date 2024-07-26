package com.kingmang.lazurite.core

import com.kingmang.lazurite.exceptions.FileInfo
import java.util.*

object CallStack {
    private val stack: Deque<CallInfo> = ArrayDeque()

    @Synchronized
    fun enter(name: String, function: Function, file: FileInfo?) {
        stack.push(CallInfo(name, function, file))
    }

    @Synchronized
    fun exit() {
        stack.pop()
    }

    @Synchronized
    fun getCalls(): Deque<CallInfo> =
        this.stack

    data class CallInfo(val name: String, val function: Function, val file: FileInfo?)
}