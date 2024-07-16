package runtime.values

import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.runtime.values.LzrNumber
import com.kingmang.lazurite.runtime.values.LzrString
import org.junit.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertNotSame
import kotlin.test.assertSame

class LzrNumberTest {

    private val testRawNumbers = listOf<Number>(
        1230.33,
        1230.33f,
        1230L,
        1230,
        1230.toShort(),
        123.toByte()
    )

    @Test
    fun type() {
        assertEquals(Types.NUMBER, LzrNumber.ZERO.type())
    }

    @Test
    fun raw() {
        assertEquals(Math.PI, LzrNumber.of(Math.PI).raw())
        testRawNumbers.forEach {
            assertEquals(it, LzrNumber.of(it).raw())
        }
    }

    @Test
    fun asBoolean() {
        assertEquals(true, LzrNumber.MINUS_ONE.asBoolean())
        assertEquals(false, LzrNumber.ZERO.asBoolean())
        assertEquals(true, LzrNumber.ONE.asBoolean())
    }

    @Test
    fun asByte() {
        testRawNumbers.forEach {
            assertEquals(it.toByte(), LzrNumber.of(it).asByte())
        }
    }

    @Test
    fun asShort() {
        testRawNumbers.forEach {
            assertEquals(it.toShort(), LzrNumber.of(it).asShort())
        }
    }

    @Test
    fun asInt() {
        testRawNumbers.forEach {
            assertEquals(it.toInt(), LzrNumber.of(it).asInt())
        }
    }

    @Test
    fun asLong() {
        testRawNumbers.forEach {
            assertEquals(it.toLong(), LzrNumber.of(it).asLong())
        }
    }

    @Test
    fun asFloat() {
        testRawNumbers.forEach {
            assertEquals(it.toFloat(), LzrNumber.of(it).asFloat())
        }
    }

    @Test
    fun asDouble() {
        testRawNumbers.forEach {
            assertEquals(it.toDouble(), LzrNumber.of(it).asDouble())
        }
    }

    @Test
    fun asNumber() {
        testRawNumbers.forEach {
            assertEquals(it.toDouble(), LzrNumber.of(it).asNumber())
        }
    }

    @Test
    fun asString() {
        testRawNumbers.forEach {
            assertEquals(it.toString(), LzrNumber.of(it).asString())
        }
    }

    @Test
    fun asArray() {
        assertContentEquals(intArrayOf(), LzrNumber.ONE.asArray())
    }

    @Test
    fun testHashCode() {
        testRawNumbers.forEach {
            assertEquals(71 * 3 + it.hashCode(), LzrNumber.of(it).hashCode())
        }
    }

    @Test
    fun testEquals() {
        testRawNumbers.forEach {
            assertEquals(LzrNumber.of(it), LzrNumber.of(it))
        }
    }

    @Test
    fun compareToNumber() {
        testRawNumbers.forEach {
            assertEquals(0, LzrNumber.of(it).compareTo(LzrNumber.of(it)), "$it to $it")
            assertEquals(-1, LzrNumber.ONE.compareTo(LzrNumber.of(it)), "${LzrNumber.ONE} to $it")
            assertEquals(1, LzrNumber.of(it).compareTo(LzrNumber.ONE), "$it to ${LzrNumber.ONE}")
        }
    }

    @Test
    fun compareToOther() {
        testRawNumbers.forEach {
            val num = LzrNumber.of(it)
            val numStr = LzrString(num.asString())
            val oneNum = LzrNumber.ONE
            val oneStr = LzrString(oneNum.asString())
            assertEquals(
                num.asString().compareTo(numStr.asString()),
                num.compareTo(numStr),
                "num $num to numstr $numStr"
            )
            assertEquals(
                oneStr.asString().compareTo(numStr.asString()),
                oneNum.compareTo(numStr),
                "onenum $oneNum to numstr $numStr"
            )
            assertEquals(
                num.asString().compareTo(oneStr.asString()),
                num.compareTo(oneStr),
                "num $num to onestr $oneStr"
            )
        }
    }

    @Test
    fun testToString() {
        testRawNumbers.forEach {
            assertEquals(LzrNumber.of(it).asString(), LzrNumber.of(it).asString())
        }
    }

    @Test
    fun fromBoolean() {
        assertEquals(LzrNumber.ONE, LzrNumber.fromBoolean(true))
        assertEquals(LzrNumber.ZERO, LzrNumber.fromBoolean(false))
    }

    @Test
    fun ofInt() {
        assertEquals(Int.MIN_VALUE, LzrNumber.of(Int.MIN_VALUE).raw())
        assertEquals(Int.MAX_VALUE, LzrNumber.of(Int.MAX_VALUE).raw())
    }

    @Test
    fun ofIntCache() {
        (-128..127).forEach {
            assertSame(LzrNumber.of(it), LzrNumber.of(it))
            assertEquals(it, LzrNumber.of(it).raw())
        }
        assertNotSame(LzrNumber.of(-129), LzrNumber.of(-129))
        assertNotSame(LzrNumber.of(128), LzrNumber.of(128))
    }

    @Test
    fun ofNumber() {
        val testRawNumbers = listOf<Pair<Number, Number>>(
            Double.MIN_VALUE to Double.MAX_VALUE,
            Float.MIN_VALUE to Float.MAX_VALUE,
            Long.MIN_VALUE to Long.MAX_VALUE,
            Int.MIN_VALUE to Int.MAX_VALUE,
            Short.MIN_VALUE to Short.MAX_VALUE,
            Byte.MIN_VALUE to Byte.MAX_VALUE
        )
        testRawNumbers.forEach {
            assertEquals(it.first, LzrNumber.of(it.first).raw())
            assertEquals(it.second, LzrNumber.of(it.second).raw())
        }
    }

    @Test
    fun integerConst() {
        assertEquals(-1, LzrNumber.MINUS_ONE.raw())
        assertEquals(0, LzrNumber.ZERO.raw())
        assertEquals(1, LzrNumber.ONE.raw())
    }
}