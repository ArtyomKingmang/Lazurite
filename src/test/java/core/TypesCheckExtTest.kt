package core

import com.kingmang.lazurite.core.*
import com.kingmang.lazurite.runtime.ClassInstanceBuilder
import com.kingmang.lazurite.runtime.values.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TypesCheckExtTest {

    private val types = listOf(
        LzrNumber.ONE to Types.NUMBER,
        LzrString("") to Types.STRING,
        LzrArray(emptyList()) to Types.ARRAY,
        LzrMap(emptyMap()) to Types.MAP,
        LzrFunction.EMPTY to Types.FUNCTION,
        ClassInstanceBuilder("Test").build(arrayOf()) to Types.CLASS
    )

    @Test
    fun `isNumber Expect true with number`() {
        assertType(Types.NUMBER) { it.isLzrNumber() }
    }

    @Test
    fun `isString Expect true with string`() {
        assertType(Types.STRING) { it.isLzrString() }
    }

    @Test
    fun `isArray Expect true with array`() {
        assertType(Types.ARRAY) { it.isLzrArray() }
    }

    @Test
    fun `isMap Expect true with map`() {
        assertType(Types.MAP) { it.isLzrMap() }
    }

    @Test
    fun `isFunction Expect true with function`() {
        assertType(Types.FUNCTION) { it.isLzrFunction() }
    }

    @Test
    fun `isClass Expect true with class`() {
        assertType(Types.CLASS) { it.isLzrClass() }
    }

    @Test
    fun `isType Expect true with type`() {
        types.forEach { (lzrValue, type) ->
            assertEquals(true, lzrValue.isLzrType(type))
        }
    }

    private fun assertType(targetType: Int, block: (LzrValue) -> Boolean) {
        types.forEach { (lzrValue, type) ->
            assertEquals(type == targetType, block.invoke(lzrValue), "valueType:${Types.typeToString(lzrValue.type())}")
        }
    }
}