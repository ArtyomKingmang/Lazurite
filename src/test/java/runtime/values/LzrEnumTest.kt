package runtime.values

import com.kingmang.lazurite.runtime.values.LzrEnum
import com.kingmang.lazurite.runtime.values.LzrFunction
import com.kingmang.lazurite.runtime.values.LzrNumber
import com.kingmang.lazurite.runtime.values.LzrString
import org.junit.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class LzrEnumTest {

    private val testMap = mapOf(
        "key1" to LzrString("value1"),
        "key2" to LzrString("value2"),
        "key3" to LzrString("value3"),
    )
    private val testLzrEnum = LzrEnum(testMap)

    @Test
    fun get() {
        testMap.entries.forEach {
            assertEquals(it.value, testLzrEnum.get(it.key))
        }
        assertEquals(null, testLzrEnum.get("key_unknown"))
    }

    @Test
    fun raw() {
        assertEquals(testMap, testLzrEnum.raw())
    }

    @Test
    fun asInt() {
        assertEquals(3, testLzrEnum.asInt())
    }

    @Test
    fun asNumber() {
        assertEquals(3.0, testLzrEnum.asNumber())
    }

    @Test
    fun asString() {
        assertEquals(testMap.toString(), testLzrEnum.asString())
    }

    @Test
    fun asArray() {
        assertContentEquals(intArrayOf(), testLzrEnum.asArray())
    }

    @Test
    fun type() {
        assertEquals(0, testLzrEnum.type())
    }

    @Test
    fun compareTo() {
        assertEquals(0, testLzrEnum.compareTo(testLzrEnum))
        assertEquals(0, testLzrEnum.compareTo(LzrString.EMPTY))
        assertEquals(0, testLzrEnum.compareTo(LzrNumber.MINUS_ONE))
        assertEquals(0, testLzrEnum.compareTo(LzrNumber.ZERO))
        assertEquals(0, testLzrEnum.compareTo(LzrNumber.ONE))
        assertEquals(0, testLzrEnum.compareTo(LzrFunction.EMPTY))
    }
}