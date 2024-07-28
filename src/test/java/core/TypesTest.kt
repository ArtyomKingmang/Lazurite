package core

import com.kingmang.lazurite.core.Types
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TypesTest {
    private val names = listOf(
        "object" to 0,
        "number" to 1,
        "string" to 2,
        "array" to 3,
        "map" to 4,
        "function" to 5,
        "class" to 6
    )

    private val types = listOf(
        Types.OBJECT to 0,
        Types.NUMBER to 1,
        Types.STRING to 2,
        Types.ARRAY to 3,
        Types.MAP to 4,
        Types.FUNCTION to 5,
        Types.CLASS to 6,
    )

    @Test
    fun `check type constants Expect valid values`() {
        types.forEach { (actual, expected) ->
            assertEquals(expected, actual)
        }
    }

    @Test
    fun `getNames Expect equals names`() {
        val expected = names.map { it.first }
        val actual = Types.NAMES.toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `typeToString with known types Expect names`() {
        names.forEach { (name, type) ->
            assertEquals(name, Types.typeToString(type), "type:$type")
        }
    }

    @Test
    fun `typeToString with unknown type Expect unknown name`() {
        val type = 100101
        assertEquals("unknown ($type)", Types.typeToString(type), "type:$type")
    }
}