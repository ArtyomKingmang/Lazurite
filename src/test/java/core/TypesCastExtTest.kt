package core

import com.kingmang.lazurite.core.*
import com.kingmang.lazurite.runtime.ClassInstanceValue
import com.kingmang.lazurite.runtime.values.*
import org.junit.Test
import testutils.assertLzrTypeFails
import kotlin.test.assertEquals

class TypesCastExtTest {

    private val types = listOf(
        LzrNumber.ONE to Types.NUMBER,
        LzrString("") to Types.STRING,
        LzrArray(emptyList()) to Types.ARRAY,
        LzrMap(emptyMap()) to Types.MAP,
        LzrFunction.EMPTY to Types.FUNCTION,
        ClassInstanceValue("Test") to Types.CLASS
    )

    private val testMessage = "Test message"

    private val testLazyMessage = { testMessage }

    @Test
    fun `asNumber Expect number or fail`() {
        assertType(Types.NUMBER) { asLzrNumber(it) }
    }

    @Test
    fun `asString Expect string or fail`() {
        assertType(Types.STRING) { asLzrString(it) }
    }

    @Test
    fun `asArray Expect array or fail`() {
        assertType(Types.ARRAY) { asLzrArray(it) }
    }

    @Test
    fun `asMap Expect map or fail`() {
        assertType(Types.MAP) { asLzrMap(it) }
    }

    @Test
    fun `asFunction Expect function or fail`() {
        assertType(Types.FUNCTION) { asLzrFunction(it) }
    }

    @Test
    fun `asClass Expect class or fail`() {
        assertType(Types.CLASS) { asLzrClass(it) }
    }

    @Test
    fun `asTypeTest Expect type or fail`() {
        types.forEach { (lzrValue, type) ->
            assertEquals(lzrValue, lzrValue.asLzrType(type, testLazyMessage))
        }
    }

    @Test
    fun `throwTypeCastException with types Expect valid fail`() {
        assertLzrTypeFails(castText("map", "string")) {
            throwTypeCastException(Types.MAP, Types.STRING)
        }
        assertLzrTypeFails(castText("string", "class")) {
            throwTypeCastException(Types.STRING, Types.CLASS)
        }
    }

    @Test
    fun `throwTypeCastException with strings Expect valid fail`() {
        assertLzrTypeFails(castText("map", "string")) {
            throwTypeCastException("map", "string")
        }
        assertLzrTypeFails(castText("string", "class")) {
            throwTypeCastException("string", "class")
        }
    }

    @Test
    fun `throwTypeException with message Expect valid fail`() {
        assertLzrTypeFails(testMessage) {
            throwTypeException(testMessage)
        }
    }

    private fun castText(origin: String, target: String): String {
        return "Cannot cast $origin to $target"
    }

    private fun assertType(targetType: Int, block: LzrValue.((() -> String)?) -> LzrValue) {
        types.forEach { (lzrValue, type) ->
            val message = "valueType:${Types.typeToString(lzrValue.type())}"
            if (type == targetType) {
                assertEquals(lzrValue, block.invoke(lzrValue, null), message)
            } else {
                assertLzrTypeFails(testMessage) {
                    block.invoke(lzrValue, testLazyMessage)
                }
            }
        }
    }
}