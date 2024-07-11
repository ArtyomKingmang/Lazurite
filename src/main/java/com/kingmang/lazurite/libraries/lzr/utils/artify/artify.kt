package com.kingmang.lazurite.libraries.lzr.utils.artify

import com.kingmang.lazurite.core.Arguments
import com.kingmang.lazurite.core.Function
import com.kingmang.lazurite.exceptions.LzrException
import com.kingmang.lazurite.libraries.Library
import com.kingmang.lazurite.libraries.lzr.utils.artify.styles.IArtifyStyle
import com.kingmang.lazurite.libraries.lzr.utils.artify.styles.impl.*
import com.kingmang.lazurite.runtime.Variables
import com.kingmang.lazurite.runtime.values.LzrMap
import com.kingmang.lazurite.runtime.values.LzrString
import java.util.*

@Suppress("unused", "ClassName")
class artify : Library {

    companion object {
        private val styles: Array<IArtifyStyle> = arrayOf(
            StarwarsStyle(),
            SwamplandStyle(),
            GeorgiaStyle(),
            SmallStyle(),
            DoomStyle(),
            BannerStyle(),
            DohStyle(),
            GothicStyle(),
            SpeedStyle(),
            SmkeyboardStyle()
        )

        fun proceed(raw: String, styleName: String): String {
            val style: IArtifyStyle = styles.find { it.styleName == styleName } ?: throw LzrException("IllegalArgumentException", "Style with name \"$styleName\" does not exist, please use one of ${styles.joinToString(", ") { it.styleName }}")
            val artMap = style.getArtMap()

            fun splitIntoLines(s: String): List<String> = s.split("\n")

            val artLinesList = mutableListOf<List<String>>()

            for (char: Char in raw.toCharArray()) {
                val artChar: String = artMap[char] ?: artMap[Character.toUpperCase(char)] ?: continue
                artLinesList.add(splitIntoLines(artChar))
            }

            val maxLines = artLinesList.maxOfOrNull { it.size } ?: 0

            val artBuilder = StringBuilder()

            for (i in 0 until maxLines) {
                for (artLines in artLinesList) {
                    if (i < artLines.size) {
                        artBuilder.append(artLines[i])
                    } else {
                        artBuilder.append(" ".repeat(artLines.firstOrNull()?.length ?: 0))
                    }
                }
                artBuilder.append("\n")
            }

            return artBuilder.toString()
        }
    }

    @Throws(LzrException::class)
    override fun init() {
        val artifyMap = LzrMap(1)

        artifyMap["build"] = Function { args ->
            Arguments.checkRange(1, 2, args.size)

            val raw = args[0].asString()
            val styleName = args.getOrNull(1)?.asString() ?: "starwars"

            LzrString(proceed(raw, styleName))
        }

        Variables.define("artify", artifyMap)
    }

    private fun splitIntoLines(s: String): List<String> = s.split("\n")
}
