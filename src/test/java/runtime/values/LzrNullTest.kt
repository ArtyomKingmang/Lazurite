package runtime.values

import com.kingmang.lazurite.runtime.values.LzrNull
import com.kingmang.lazurite.runtime.values.LzrString
import org.junit.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class LzrNullTest {

    private val testNull = LzrNull()

    @Test
    fun raw() {
        assertEquals(null, testNull.raw())
    }

    @Test
    fun asInt() {
        assertEquals(0, testNull.asInt())
    }

    @Test
    fun asNumber() {
        assertEquals(0.0, testNull.asNumber())
    }

    @Test
    fun asString() {
        assertEquals("null", testNull.asString())
    }

    @Test
    fun asArray() {
        assertContentEquals(intArrayOf(), testNull.asArray())
    }

    @Test
    fun type() {
        assertEquals(482862660, testNull.type())
    }

    @Test
    fun compareTo() {
        assertEquals(0, testNull.compareTo(LzrNull()))
        assertEquals(-1, testNull.compareTo(LzrString.EMPTY))
    }

    @Test
    fun testToString() {
        assertEquals(testNull.asString(), testNull.toString())
    }
}