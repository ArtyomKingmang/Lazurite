package com.kingmang.lazurite.libraries.lzr.utils.artify

import com.kingmang.lazurite.core.Arguments
import com.kingmang.lazurite.core.Function
import com.kingmang.lazurite.libraries.Library
import com.kingmang.lazurite.runtime.Variables
import com.kingmang.lazurite.runtime.values.LzrMap
import com.kingmang.lazurite.runtime.values.LzrString
import java.util.*

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
            val lines = arrayOfNulls<StringBuilder>(4)
            for (i in 0..3) {
                lines[i] = StringBuilder()
            }
            for (i in 0 until raw.length) {
                val c = raw[i]
                if (c >= 'a' && c <= 'z') {
                    val index = c.code - 'a'.code
                    for (j in 0..3) {
                        lines[j]!!.append(letters[j][index])
                    }
                } else if (c == '.') {
                    for (j in 0..3) {
                        lines[j]!!.append(letters[j][36])
                    }
                } else if (c == ',') {
                    for (j in 0..3) {
                        lines[j]!!.append(letters[j][37])
                    }
                } else if (c == ' ') {
                    for (j in 0..3) {
                        lines[j]!!.append("     ")
                    }
                } else if (c == '!') {
                    for (j in 0..3) {
                        lines[j]!!.append(letters[j][38])
                    }
                } else if (c == '/') {
                    for (j in 0..3) {
                        lines[j]!!.append(letters[j][39])
                    }
                } else if (c == '<') {
                    for (j in 0..3) {
                        lines[j]!!.append(letters[j][40])
                    }
                } else if (c == '>') {
                    for (j in 0..3) {
                        lines[j]!!.append(letters[j][41])
                    }
                } else if (c == '?') {
                    for (j in 0..3) {
                        lines[j]!!.append(letters[j][42])
                    }
                } else if (c == '\\') {
                    for (j in 0..3) {
                        lines[j]!!.append(letters[j][43])
                    }
                } else if (c == ':') {
                    for (j in 0..3) {
                        lines[j]!!.append(letters[j][44])
                    }
                } else if (c == '1') {
                    for (j in 0..3) {
                        lines[j]!!.append(letters[j][26])
                    }
                } else if (c == '2') {
                    for (j in 0..3) {
                        lines[j]!!.append(letters[j][27])
                    }
                } else if (c == '3') {
                    for (j in 0..3) {
                        lines[j]!!.append(letters[j][28])
                    }
                } else if (c == '4') {
                    for (j in 0..3) {
                        lines[j]!!.append(letters[j][29])
                    }
                } else if (c == '5') {
                    for (j in 0..3) {
                        lines[j]!!.append(letters[j][30])
                    }
                } else if (c == '6') {
                    for (j in 0..3) {
                        lines[j]!!.append(letters[j][31])
                    }
                } else if (c == '7') {
                    for (j in 0..3) {
                        lines[j]!!.append(letters[j][32])
                    }
                } else if (c == '8') {
                    for (j in 0..3) {
                        lines[j]!!.append(letters[j][33])
                    }
                } else if (c == '9') {
                    for (j in 0..3) {
                        lines[j]!!.append(letters[j][34])
                    }
                } else if (c == '0') {
                    for (j in 0..3) {
                        lines[j]!!.append(letters[j][35])
                    }
                }
            }
            val result = StringBuilder()
            for (i in 0..3) {
                result.append(lines[i]).append("\n")
            }
            LzrString(result.toString())
        }

        Variables.define("artify", map)
    }
}
