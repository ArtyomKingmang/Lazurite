package com.kingmang.lazurite.parser.preprocessor

import com.kingmang.lazurite.runner.RunnerInfo
import com.kingmang.lazurite.utils.Handler
import java.util.*

object Preprocessor {
    @JvmStatic
    fun preprocess(code: String): String {
        var code = code
        var lines = Arrays.asList(*code.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
        val macros: MutableMap<String, String> = HashMap()

        macros["Lazurite"] = "\"" + RunnerInfo.getVersion() + "\""

        val codeBuilder = StringBuilder(code)
        for (line in lines) {
            val parts = Arrays.asList(*line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
            if (parts.isEmpty()) continue

            val command = parts[0]
            when (command) {
                "#define" -> {
                    val id = parts[1]
                    val replacement = java.lang.String.join(" ", parts.subList(2, parts.size))
                    if (id.contains("(")) {
                        codeBuilder.insert(0, "\nfunc $id return $replacement\n")
                        continue
                    }
                    macros[id] = replacement
                }

                "#ifdef" -> {
                    val idIfdef = parts[1]
                    val replacementIfdef = java.lang.String.join(" ", parts.subList(2, parts.size))
                    if (macros.containsKey(idIfdef)) Handler.handle(replacementIfdef, "<preprocessor>", true, false)
                }

                "#ifndef" -> {
                    val idIfndef = parts[1]
                    val replacementIfndef = java.lang.String.join(" ", parts.subList(2, parts.size))
                    if (!macros.containsKey(idIfndef)) Handler.handle(replacementIfndef, "<preprocessor>", true, false)
                }

                else -> {}
            }
        }
        code = codeBuilder.toString()

        lines = Arrays.asList(*code.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
        var result = StringBuilder()

        for (line in lines) {
            var buffer = line
            val parts = Arrays.asList(*line.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
            if (parts[0] == "#undef") {
                val idUndef = parts[1]
                macros.remove(idUndef)
                continue
            }

            for ((key, value) in macros) {
                buffer = buffer.replace(";".toRegex(), " ;")
                buffer = buffer.replace(String.format("(?<!\")(%s)(?!\")", key).toRegex(), value) + "\n"
            }
            result.append(buffer)
        }

        result = StringBuilder(
            result.toString()
                .replace("#define", "# PROCESSED DEFINE MACROS")
                .replace("#ifdef", "# PROCESSED IFDEF MACROS")
                .replace("#ifndef", "# PROCESSED IFNDEF MACRO")
                .replace("#undef", "# PROCESSED UNDEF MACRO")
        )

        return result.toString()
    }
}