package core

import com.kingmang.lazurite.core.*
import com.kingmang.lazurite.runtime.ClassInstanceBuilder
import com.kingmang.lazurite.runtime.values.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TypesSafeCastExtTest {
    private val types = listOf(
        LzrNumber.ONE to Types.NUMBER,
        LzrString("") to Types.STRING,
        LzrArray(emptyList()) to Types.ARRAY,
        LzrMap(emptyMap()) to Types.MAP,
        LzrFunction.EMPTY to Types.FUNCTION,
        ClassInstanceBuilder("Test").build(arrayOf()) to Types.CLASS
    )

    @Test
    fun `asNumberOrNull Expect valid number`() {
        assertType(Types.NUMBER) { it.asLzrNumberOrNull() }
    }

    @Test
    fun `asStringOrNull Expect valid string`() {
        assertType(Types.STRING) { it.asLzrStringOrNull() }
    }

    @Test
    fun `asArrayOrNull Expect valid array`() {
        assertType(Types.ARRAY) { it.asLzrArrayOrNull() }
    }

    @Test
    fun `asMapOrNull Expect valid map`() {
        assertType(Types.MAP) { it.asLzrMapOrNull() }
    }

    @Test
    fun `asFunctionOrNull Expect valid function`() {
        assertType(Types.FUNCTION) { it.asLzrFunctionOrNull() }
    }

    @Test
    fun `asClassOrNull Expect valid class`() {
        assertType(Types.CLASS) { it.asLzrClassOrNull() }
    }

    @Test
    fun `asTypeOrNull Expect valid type`() {
        types.forEach { (lzrValue, type) ->
            assertEquals(lzrValue, lzrValue.asLzrTypeOrNull(type))
        }
    }

    private fun assertType(targetType: Int, block: (LzrValue) -> LzrValue?) {
        types.forEach { (lzrValue, type) ->
            val actual = block.invoke(lzrValue)
            val message = "valueType:${Types.typeToString(lzrValue.type())}"
            if (type == targetType) {
                assertEquals(lzrValue, actual, message)
            } else {
                assertEquals(null, actual, message)
            }
        }
    }
}