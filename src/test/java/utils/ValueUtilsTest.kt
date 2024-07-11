package utils

import com.kingmang.lazurite.core.Function
import com.kingmang.lazurite.runtime.values.*
import com.kingmang.lazurite.utils.ValueUtils
import org.json.JSONArray
import org.json.JSONObject
import org.junit.Test
import testutils.assertLzrTypeFails
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class ValueUtilsTest {

    @Test
    fun `toObject with list of input Expect all success`() {
        val pairs = listOf<Pair<LzrValue, Any>>(
            LzrArray(listOf(LzrNumber.of(123))) to JSONArray(listOf(123)),
            LzrMap(mapOf(LzrString("key") to LzrString("value"))) to JSONObject(mapOf("key" to "value")),
            LzrNumber.of(123.4) to 123.4,
            LzrString("value") to "value",
            // Some cases with NULL result
            LzrEnum(mapOf("key" to LzrString("value"))) to JSONObject.NULL,
            LzrFunction.EMPTY to JSONObject.NULL,
            LzrNull() to JSONObject.NULL,
        )
        pairs.forEach { (input, expected) ->
            val actual = ValueUtils.toObject(input)
            assertJson(expected, actual, "input ${input::class.simpleName}:$input")
        }
    }

    @Test
    fun `toValue with list of input Expect all success`() {
        val pairs = listOf<Pair<Any, LzrValue>>(
            JSONArray(listOf(123)) to LzrArray(listOf(LzrNumber.of(123))),
            JSONObject(mapOf("key" to "value")) to LzrMap(mapOf(LzrString("key") to LzrString("value"))),
            "value" to LzrString("value"),
            123.4 to LzrNumber.of(123.4),
            false to LzrNumber.ZERO,
            true to LzrNumber.ONE,
            // Some cases with ZERO result
            JSONObject.NULL to LzrNumber.ZERO,
            ValueUtils to LzrNumber.ZERO
        )
        pairs.forEach { (input, expected) ->
            val actual = ValueUtils.toValue(input)
            assertJson(expected, actual, "input ${input::class.simpleName}:$input")
        }
    }

    @Test
    fun `getNumber with LzrNumber Expect raw number`() {
        assertEquals(123.4, ValueUtils.getNumber(LzrNumber.of(123.4)))
        assertEquals(123, ValueUtils.getNumber(LzrNumber.of(123)))
    }

    @Test
    fun `getNumber with LzrMap Expect fail`() {
        assertLzrTypeFails("Cannot cast map to integer") {
            ValueUtils.getNumber(LzrMap(emptyMap()))
        }
    }

    @Test
    fun `getNumberFloat with LzrNumber Expect floats`() {
        assertEquals(123.4f, ValueUtils.getFloatNumber(LzrNumber.of(123.4f)))
        assertEquals(123f, ValueUtils.getFloatNumber(LzrNumber.of(123)))
    }

    @Test
    fun `getNumberFloat with LzrMap Expect fail`() {
        assertLzrTypeFails("Cannot cast map to number") {
            ValueUtils.getFloatNumber(LzrMap(emptyMap()))
        }
    }

    @Test
    fun `toByteArray with integers Expect bytes`() {
        val input = listOf(1, 2, 3, 4, 5, 100000)
        val expects = input.map { it.toByte() }.toByteArray()
        val array = LzrArray(input.map { LzrNumber.of(it) })
        val actual = ValueUtils.toByteArray(array)
        assertContentEquals(expects, actual)
    }

    @Test
    fun `consumeFunction with LzrFunction Expect Function`() {
        val expected = Function { LzrNumber.ONE }
        val actual = ValueUtils.consumeFunction(LzrFunction(expected), 1)
        assertEquals(expected, actual)
    }

    @Test
    fun `consumeFunction with LzrNumber Expect fail`() {
        assertLzrTypeFails("Function expected at argument 2, but found number") {
            ValueUtils.consumeFunction(LzrNumber.ONE, 1)
        }
    }

    private fun assertJson(expected: Any?, actual: Any?, message: String? = null) {
        when {
            expected is JSONObject && actual is JSONObject -> assertEquals(expected.toMap(), actual.toMap(), message)
            expected is JSONArray && actual is JSONArray -> assertEquals(expected.toList(), actual.toList(), message)
            else -> assertEquals(expected, actual, message)
        }
    }
}