package com.kingmang.lazurite.parser.preprocessor

import com.kingmang.lazurite.runner.RunnerInfo

object Preprocessor {
    @JvmStatic
    fun preprocess(code: String): String {
        val macros: MutableMap<String, String> = HashMap()
        macros["lzrVersion"] = "\"" + RunnerInfo.VERSION + "\""

        val processedCode = StringBuilder()

        val lines = code.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (line in lines) {
            val trimmed = line.trim { it <= ' ' }
            when {
                trimmed.startsWith("#define") -> {
                    val parts = line.trim { it <= ' ' }.split("\\s+".toRegex(), limit = 3).toTypedArray()
                    if (parts.size == 3) {
                        macros[parts[1]] = parts[2]
                    }
                }
                trimmed.startsWith("#include") -> {
                    val parts = line.trim { it <= ' ' }.split("\\s+".toRegex(), limit = 2).toTypedArray()
                    if (parts.size == 2) {
                        processedCode.append("using ").append(parts[1]).append("\n")
                    }
                }
                trimmed.startsWith("#jInclude") -> {
                    val parts = line.trim { it <= ' ' }.split("\\s+".toRegex(), limit = 2).toTypedArray()
                    if (parts.size == 2) {
                        val partsOfPkg = parts[1].split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                        val template = "%s = JClass(%s)"
                        processedCode.append("using \"lzr.lang.reflection\"; ")
                        processedCode.append(String.format(template, partsOfPkg[2].replace("\"".toRegex(), ""), parts[1]))
                        processedCode.append("\n")
                    }
                }
                else -> {
                    var lastLine = line
                    for ((key, value) in macros)
                        lastLine = lastLine.replace(key, value)
                    processedCode.append(lastLine).append("\n")
                }
            }
        }

        return processedCode.toString()
    }
}