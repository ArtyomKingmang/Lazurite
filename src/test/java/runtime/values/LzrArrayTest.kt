package runtime.values

import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.core.asLzrFunction
import com.kingmang.lazurite.runtime.values.LzrArray
import com.kingmang.lazurite.runtime.values.LzrNumber
import com.kingmang.lazurite.runtime.values.LzrString
import com.kingmang.lazurite.runtime.values.LzrValue
import org.junit.Assert.assertArrayEquals
import org.junit.Test
import testutils.assertLzrTypeCastFails
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertNotEquals

class LzrArrayTest {

    private val defaultArray = arrayOf<LzrValue>(LzrNumber.MINUS_ONE, LzrNumber.ZERO, LzrNumber.ONE)
    private val defaultLzrArray = LzrArray(defaultArray)
    private val emptyLzrArray = LzrArray(0)

    @Test
    fun getCopyElements() {
        val actual = defaultLzrArray.copyElements
        assertArrayEquals(defaultArray, actual)
        assertNotEquals(defaultArray, actual)
    }

    @Test
    fun type() {
        assertEquals(Types.ARRAY, defaultLzrArray.type())
    }

    @Test
    fun size() {
        assertEquals(3, defaultLzrArray.size())
        assertEquals(0, emptyLzrArray.size())
    }

    @Test
    fun getByInt() {
        defaultArray.forEachIndexed { index, lzrValue ->
            assertEquals(lzrValue, defaultLzrArray[index])
        }
        assertFails { defaultLzrArray[-1] }
        assertFails { defaultLzrArray[defaultLzrArray.size()] }
    }

    @Test
    fun getByLzrValue() {
        assertEquals(LzrNumber.of(3), defaultLzrArray[LzrString("length")])
        assertEquals(LzrNumber.of(0), emptyLzrArray[LzrString("length")])

        val isEmptyDefault = defaultLzrArray[LzrString("isEmpty")].asLzrFunction()
        val isEmptyEmpty = emptyLzrArray[LzrString("isEmpty")].asLzrFunction()
        assertEquals(LzrNumber.fromBoolean(false), isEmptyDefault.value.execute())
        assertEquals(LzrNumber.fromBoolean(true), isEmptyEmpty.value.execute())

        defaultArray.forEachIndexed { index, lzrValue ->
            assertEquals(lzrValue, defaultLzrArray[LzrNumber.of(index)])
        }
        assertFails { defaultLzrArray[LzrNumber.of(-1)] }
        assertFails { defaultLzrArray[LzrNumber.of(defaultLzrArray.size())] }
    }

    @Test
    fun set() {
        val lzrArray = LzrArray(defaultLzrArray.copyElements)
        val expected = (0 until lzrArray.size()).map {
            LzrString("value$it")
        }
        expected.forEachIndexed { index, lzrString ->
            lzrArray[index] = lzrString
            assertEquals(lzrString, lzrArray[index])
        }
    }

    @Test
    fun raw() {
        assertContentEquals(defaultArray, defaultLzrArray.raw())
    }

    @Test
    fun asInt() {
        assertLzrTypeCastFails("array", "integer") {
            defaultLzrArray.asInt()
        }
    }

    @Test
    fun asNumber() {
        assertLzrTypeCastFails("array", "number") {
            defaultLzrArray.asNumber()
        }
    }

    @Test
    fun asString() {
        assertEquals(defaultArray.contentToString(), defaultLzrArray.asString())
    }

    @Test
    fun asArray() {
        assertArrayEquals(intArrayOf(), defaultLzrArray.asArray())
    }

    @Test
    operator fun iterator() {
        assertEquals(defaultArray.toList(), defaultLzrArray.toList())
    }

    @Test
    fun testHashCode() {
        val hash = 79 * 5 + defaultArray.contentDeepHashCode()
        assertEquals(hash, defaultLzrArray.hashCode())
    }

    @Test
    fun testEquals() {
        assertEquals(true, defaultLzrArray == LzrArray(defaultArray.copyOf()))
        assertEquals(false, defaultLzrArray == LzrArray(0))
        assertEquals(false, defaultLzrArray.equals(LzrNumber.MINUS_ONE))
    }

    @Test
    fun compareTo() {
        val number = LzrNumber.ZERO
        val defaultStr = LzrString(defaultLzrArray.asString())
        assertEquals(1, defaultLzrArray.compareTo(emptyLzrArray))
        assertEquals(-1, emptyLzrArray.compareTo(defaultLzrArray))
        assertEquals(43, defaultLzrArray.compareTo(number))
        assertEquals(-43, number.compareTo(defaultLzrArray))
        assertEquals(0, defaultLzrArray.compareTo(defaultStr))
        assertEquals(0, defaultStr.compareTo(defaultLzrArray))
    }

    @Test
    fun testToString() {
        assertEquals(defaultLzrArray.asString(), defaultLzrArray.toString())
    }

    @Test
    fun ofVarArgs() {
        val actual = LzrArray.of(LzrString("val1"), LzrString("val2"), LzrString("val3"))
        val expected = LzrArray(arrayOf(LzrString("val1"), LzrString("val2"), LzrString("val3")))
        assertEquals(expected, actual)
    }

    @Test
    fun ofByteArray() {
        val bytes = byteArrayOf(1, 2, 3, -1, -2, -3, 10, 100, -100)
        val actual = LzrArray.of(bytes)
        val expected = LzrArray(bytes.map { LzrNumber.of(it.toInt()) })
        assertEquals(expected, actual)
    }

    @Test
    fun ofStringArray() {
        val strings = arrayOf("val1", "val2", "val-1", "val4")
        val actual = LzrArray.of(strings)
        val expected = LzrArray(strings.map { LzrString(it) })
        assertEquals(expected, actual)
    }

    @Test
    fun add() {
        val base = arrayOf("val1", "val2", "val-1", "val4")
        val baseLzr = LzrArray.of(base)
        val actual = LzrArray.add(baseLzr, LzrString("val5"))
        val expected = LzrArray((base + "val5").map { LzrString(it) })
        assertEquals(expected, actual)
    }

    @Test
    fun merge() {
        val arr1 = arrayOf("val1", "val2", "val3")
        val arr2 = arrayOf("val4", "val5", "val6")
        val actual = LzrArray.merge(LzrArray.of(arr1), LzrArray.of(arr2))
        val expected = LzrArray((arr1 + arr2).map { LzrString(it) })
        assertEquals(expected, actual)
    }

    @Test
    fun joinToString() {
        val data = LzrArray.of(arrayOf("val1", "val2", "val3"))
        assertEquals("val1val2val3", LzrArray.joinToString(data).toString())
        assertEquals("preval1delval2delval3pos", LzrArray.joinToString(data,"del", "pre", "pos").toString())
    }
}