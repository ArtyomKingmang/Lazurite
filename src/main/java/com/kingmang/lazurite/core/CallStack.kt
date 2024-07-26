package com.kingmang.lazurite.core

import com.kingmang.lazurite.exceptions.FileInfo
import java.util.*

class CallStack {
    private val stack: Deque<CallInfo> = ArrayDeque()

    fun enter(name: String, function: Function, file: FileInfo?) {
        stack.push(CallInfo(name, function, file))
    }

    fun exit() {
        stack.pop()
    }

    fun getCalls(): Deque<CallInfo> =
        this.stack

    companion object {
        @JvmStatic
        val stack: ThreadLocal<CallStack> = ThreadLocal.withInitial(::CallStack)

        fun enter(name: String, function: Function, file: FileInfo?) {
            stack.get().enter(name, function, file)
        }

        fun exit() {
            stack.get().exit()
        }

        fun getCalls(): Deque<CallInfo> =
            this.stack.get().getCalls()
    }

    data class CallInfo(val name: String, val function: Function, val file: FileInfo?)
}