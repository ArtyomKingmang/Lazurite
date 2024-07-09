package com.kingmang.lazurite.libraries.lzr.utils.artify

import com.kingmang.lazurite.core.Arguments
import com.kingmang.lazurite.core.Function
import com.kingmang.lazurite.libraries.Library
import com.kingmang.lazurite.runtime.Variables
import com.kingmang.lazurite.runtime.values.LzrMap
import com.kingmang.lazurite.runtime.values.LzrString
import java.util.*

@Suppress("unused", "ClassName")
class artify : Library {
    private val letters = arrayOf(
        arrayOf(
            "    _   ",
            "  ___ ",
            "   ___ ",
            "  ___  ",
            "  ___ ",
            "  ___ ",
            "   ___ ",
            "  _  _ ",
            "  ___ ",
            "     _ ",
            "  _  __",
            "  _    ",
            "  __  __ ",
            "  _  _ ",
            "   ___  ",
            "  ___ ",
            "   ___  ",
            "  ___ ",
            "  ___ ",
            "  _____ ",
            "  _   _ ",
            " __   __",
            " __      __",
            " __  __",
            " __   __",
            "  ____",
            "  _ ",
            "  ___ ",
            "  ____",
            "  _ _  ",
            "  ___ ",
            "   __ ",
            "  ____ ",
            "  ___ ",
            "  ___ ",
            "   __  ",
            "    ",
            "    ",
            "  _ ",
            "    __",
            "   __",
            " __  ",
            "  ___ ",
            " __   ",
            "  _ "
        ),
        arrayOf(
            "   /_\\  ",
            " | _ )",
            "  / __|",
            " |   \\ ",
            " | __|",
            " | __|",
            "  / __|",
            " | || |",
            " |_ _|",
            "  _ | |",
            " | |/ /",
            " | |   ",
            " |  \\/  |",
            " | \\| |",
            "  / _ \\ ",
            " | _ \\",
            "  / _ \\ ",
            " | _ \\",
            " / __|",
            " |_   _|",
            " | | | |",
            " \\ \\ / /",
            " \\ \\    / /",
            " \\ \\/ /",
            " \\ \\ / /",
            " |_  /",
            " / |",
            " |_  )",
            " |__ /",
            " | | | ",
            " | __|",
            "  / / ",
            " |__  |",
            " ( _ )",
            " / _ \\",
            "  /  \\ ",
            "    ",
            "  _ ",
            " | |",
            "   / /",
            "  / /",
            " \\ \\ ",
            " |__ \\",
            " \\ \\  ",
            " (_)"
        ),
        arrayOf(
            "  / _ \\ ",
            " | _ \\",
            " | (__ ",
            " | |) |",
            " | _| ",
            " | _| ",
            " | (_ |",
            " | __ |",
            "  | | ",
            " | || |",
            " | ' < ",
            " | |__ ",
            " | |\\/| |",
            " | .` |",
            " | (_) |",
            " |  _/",
            " | (_) |",
            " |   /",
            " \\__ \\",
            "   | |  ",
            " | |_| |",
            "  \\ V / ",
            "  \\ \\/\\/ / ",
            "  >  < ",
            "  \\ V / ",
            "  / / ",
            " | |",
            "  / / ",
            "  |_ \\",
            " |_  _|",
            " |__ \\",
            " / _ \\",
            "   / / ",
            " / _ \\",
            " \\_, /",
            " | () |",
            "  _ ",
            " ( )",
            " |_|",
            "  / / ",
            " < < ",
            "  > >",
            "   /_/",
            "  \\ \\ ",
            "  _ "
        ),
        arrayOf(
            " /_/ \\_\\",
            " |___/",
            "  \\___|",
            " |___/ ",
            " |___|",
            " |_|  ",
            "  \\___|",
            " |_||_|",
            " |___|",
            "  \\__/ ",
            " |_|\\_\\",
            " |____|",
            " |_|  |_|",
            " |_|\\_|",
            "  \\___/ ",
            " |_|  ",
            "  \\__\\_\\",
            " |_|_\\",
            " |___/",
            "   |_|  ",
            "  \\___/ ",
            "   \\_/  ",
            "   \\_/\\_/  ",
            " /_/\\_\\",
            "   |_|  ",
            " /___|",
            " |_|",
            " /___|",
            " |___/",
            "   |_| ",
            " |___/",
            " \\___/",
            "  /_/  ",
            " \\___/",
            "  /_/ ",
            "  \\__/ ",
            " (_)",
            " |/ ",
            " (_)",
            " /_/  ",
            "  \\_\\",
            " /_/ ",
            "  (_) ",
            "   \\_\\",
            " (_)"
        )
    )

    override fun init() {
        val map = LzrMap(1)
        map["build"] = Function { args ->
            Arguments.check(1, args.size)
            val raw = args[0].asString().lowercase(Locale.getDefault())
            val lines = Array(4) {
                StringBuilder()
            }
            for (element in raw) {
                when (element) {
                    in 'a'..'z' -> {
                        val index = element.code - 'a'.code
                        lines.appendLetters(index)
                    }

                    '.' -> lines.appendLetters(36)
                    ',' -> lines.appendLetters(37)
                    ' ' -> lines.appendString("     ")
                    '!' -> lines.appendLetters(38)
                    '/' -> lines.appendLetters(39)
                    '<' -> lines.appendLetters(40)
                    '>' -> lines.appendLetters(41)
                    '?' -> lines.appendLetters(42)
                    '\\' -> lines.appendLetters(43)
                    ':' -> lines.appendLetters(44)
                    '1' -> lines.appendLetters(26)
                    '2' -> lines.appendLetters(27)
                    '3' -> lines.appendLetters(28)
                    '4' -> lines.appendLetters(29)
                    '5' -> lines.appendLetters(30)
                    '6' -> lines.appendLetters(31)
                    '7' -> lines.appendLetters(32)
                    '8' -> lines.appendLetters(33)
                    '9' -> lines.appendLetters(34)
                    '0' -> lines.appendLetters(35)
                }
            }
            val result = lines.fold(StringBuilder()) { acc, line ->
                acc.append(line).append('\n')
                acc
            }
            LzrString(result.toString())
        }

        Variables.define("artify", map)
    }

    private fun Array<StringBuilder>.appendLetters(index: Int) {
        for (j in indices) {
            get(j).append(letters[j][index])
        }
    }

    private fun Array<StringBuilder>.appendString(value: String) {
        for (j in indices) {
            get(j).append(value)
        }
    }
}
