package com.kingmang.lazurite.exceptions

import com.kingmang.lazurite.core.CallStack
import java.io.File
import java.io.PrintStream
import java.nio.file.Files
import java.util.ArrayDeque
import java.util.Deque

class LzrTracedException(type: String, text: String, val info: TraceInfo) : LzrException(type, text) {
    override fun print(stream: PrintStream) {
        stream.printf("[%s \"%s\"\n", type, message)
        val cache = HashMap<String, Array<String>>()
        info.thrower?.let { print(stream, cache, it, "[throw]") }
        info.stack.forEach {
            if (it.file == null)
                stream.printf("| %s\n", it.name)
            else print(stream, cache, it.file!!, it.name)
        }
        stream.println(']')
    }

    private fun print(stream: PrintStream, cache: MutableMap<String, Array<String>>, file: FileInfo, message: String) {
        stream.printf("| [%s, %s] %s\n", file.name, file.line, message)
        stream.printf("|> %s\n", cache.getOrPut(file.name) { Files.lines(File(file.name).toPath()).toArray{ arrayOfNulls<String>(it) } }[file.line - 1].trim())
    }

    class TraceInfo(val thrower: FileInfo?, stack: Deque<CallStack.CallInfo>) {
        val stack: Deque<CallStack.CallInfo> = ArrayDeque(stack)
    }
}