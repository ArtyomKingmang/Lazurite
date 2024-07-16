package runtime.values

import com.kingmang.lazurite.core.Function
import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.runtime.values.LzrFunction
import com.kingmang.lazurite.runtime.values.LzrString
import org.junit.Test
import testutils.assertLzrTypeCastFails
import java.util.*
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class LzrFunctionTest {

    private val testFunc = Function { LzrString("result") }
    private val testLzrFunc = LzrFunction(testFunc)

    @Test
    fun type() {
        assertEquals(Types.FUNCTION, testLzrFunc.type())
    }

    @Test
    fun raw() {
        assertEquals(testFunc, testLzrFunc.raw())
    }

    @Test
    fun asInt() {
        assertLzrTypeCastFails("function", "integer") {
            testLzrFunc.asInt()
        }
    }

    @Test
    fun asNumber() {
        assertLzrTypeCastFails("function", "number") {
            testLzrFunc.asNumber()
        }
    }

    @Test
    fun asString() {
        assertEquals(testFunc.toString(), testLzrFunc.asString())
    }

    @Test
    fun asArray() {
        assertContentEquals(intArrayOf(), testLzrFunc.asArray())
    }

    @Test
    fun testHashCode() {
        assertEquals(71 * 7 + Objects.hashCode(testFunc), testLzrFunc.hashCode())
    }

    @Test
    fun testEquals() {
        assertEquals(true, testLzrFunc == testLzrFunc)
        assertEquals(true, testLzrFunc == LzrFunction(testFunc))
        assertEquals(false, testLzrFunc == LzrFunction { LzrString.EMPTY })
    }

    @Test
    fun compareTo() {
        val other1 = LzrFunction(testFunc)
        val other2 = LzrFunction { LzrString.EMPTY }
        assertEquals(testLzrFunc.asString().compareTo(testLzrFunc.asString()), testLzrFunc.compareTo(testLzrFunc))
        assertEquals(testLzrFunc.asString().compareTo(other1.asString()), testLzrFunc.compareTo(other1))
        assertEquals(testLzrFunc.asString().compareTo(other2.asString()), testLzrFunc.compareTo(other2))
    }

    @Test
    fun testToString() {
        assertEquals(testLzrFunc.asString(), testLzrFunc.toString())
    }

    @Test
    fun getValue() {
        assertEquals(testFunc, testLzrFunc.value)
    }
}