package runtime.values

import com.kingmang.lazurite.core.Types
import com.kingmang.lazurite.core.asLzrFunction
import com.kingmang.lazurite.exceptions.LzrException
import com.kingmang.lazurite.libraries.Keyword
import com.kingmang.lazurite.runtime.values.LzrArray
import com.kingmang.lazurite.runtime.values.LzrNumber
import com.kingmang.lazurite.runtime.values.LzrString
import com.kingmang.lazurite.runtime.values.LzrValue
import org.junit.Test
import testutils.assertLzrFails
import java.util.*
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith


class LzrStringTest {

    private val testStr = "  test_STR ING  "
    private val testRegex = Regex("  [tes]+(_STR)\\sING  ")
    private val testLzrStr = LzrString(testStr)

    @Test
    fun accessLength() {
        assertEquals(
            LzrNumber.of(testStr.length),
            testLzrStr.access(LzrString("length"))
        )
    }

    @Test
    fun accessToLowerCase() {
        assertEquals(
            LzrString(testStr.lowercase(Locale.getDefault())),
            testLzrStr.access(LzrString("toLowerCase"))
        )
    }

    @Test
    fun accessToUpperCase() {
        assertEquals(
            LzrString(testStr.uppercase(Locale.getDefault())),
            testLzrStr.access(LzrString("toUpperCase"))
        )
    }

    @Test
    fun accessChars() {
        val expected = LzrArray(testStr.length) {
            LzrNumber.of(testStr[it].code)
        }
        assertEquals(expected, testLzrStr.access(LzrString("chars")))
    }

    @Test
    fun accessTrim() {
        assertEquals(LzrString(testStr.trim()), testLzrStr.access(LzrString("trim")).call())
    }

    @Test
    fun accessStartsWith() {
        assertEquals(LzrNumber.ONE, testLzrStr.access(LzrString("startsWith")).call(LzrString("  test")))
        assertEquals(LzrNumber.ONE, testLzrStr.access(LzrString("startsWith")).call(LzrString("test"), LzrNumber.of(2)))
        assertEquals(LzrNumber.ZERO, testLzrStr.access(LzrString("startsWith")).call(LzrString("not start")))
    }

    @Test
    fun accessEndsWith() {
        assertEquals(LzrNumber.ONE, testLzrStr.access(LzrString("endsWith")).call(LzrString("STR ING  ")))
        assertEquals(LzrNumber.ZERO, testLzrStr.access(LzrString("endsWith")).call(LzrString("not end")))
    }

    @Test
    fun accessMatches() {
        assertEquals(LzrNumber.ONE, testLzrStr.access(LzrString("matches")).call(LzrString(testRegex.pattern)))
        assertEquals(LzrNumber.ZERO, testLzrStr.access(LzrString("matches")).call(LzrString("smh another")))
    }

    @Test
    fun accessEquals() {
        assertEquals(LzrNumber.ONE, testLzrStr.access(LzrString("equals")).call(LzrString(testStr)))
        assertEquals(LzrNumber.ONE, testLzrStr.access(LzrString("equals")).call(LzrString(testStr.lowercase())))
        assertEquals(LzrNumber.ZERO, testLzrStr.access(LzrString("equals")).call(LzrString("smh another")))
    }

    @Test
    fun accessIsEmpty() {
        assertEquals(LzrNumber.ONE, LzrString("").access(LzrString("isEmpty")).call())
        assertEquals(LzrNumber.ZERO, LzrString(" ").access(LzrString("isEmpty")).call())
        assertEquals(LzrNumber.ZERO, testLzrStr.access(LzrString("isEmpty")).call())
    }

    @Test
    fun accessKeyword() {
        val testKeyword = "test_keyword"
        val arg = "arg1"
        val expected = "keyword_result"
        Keyword.put(testKeyword) { args ->
            assertEquals(2, args.size, "arguments size")
            assertEquals(testLzrStr, args[0], "first arg is this string")
            assertEquals(LzrString(arg), args[1], "second arg is passed arg")
            LzrString(expected)
        }
        assertEquals(LzrString(expected), testLzrStr.access(LzrString(testKeyword)).call(LzrString(arg)))
    }

    @Test
    fun accessUnknown() {
        val prop = "unknown_prop_123"
        assertLzrFails(LzrException("UnknownPropertyException", prop)) {
            testLzrStr.access(LzrString(prop))
        }
    }

    @Test
    fun length() {
        assertEquals(testStr.length, testLzrStr.length())
    }

    @Test
    fun type() {
        assertEquals(Types.STRING, testLzrStr.type())
    }

    @Test
    fun raw() {
        assertEquals(testStr, testLzrStr.raw())
    }

    @Test
    fun asInt() {
        assertFailsWith<NumberFormatException> {
            LzrString("not a number 100%").asInt()
        }
        assertEquals(Int.MIN_VALUE, LzrString(Int.MIN_VALUE.toString()).asInt())
        assertEquals(Int.MAX_VALUE, LzrString(Int.MAX_VALUE.toString()).asInt())
    }

    @Test
    fun asNumber() {
        assertFailsWith<NumberFormatException> {
            LzrString("not a number 100.0%").asNumber()
        }
        assertEquals(Double.MIN_VALUE, LzrString(Double.MIN_VALUE.toString()).asNumber())
        assertEquals(Double.MAX_VALUE, LzrString(Double.MAX_VALUE.toString()).asNumber())
    }

    @Test
    fun asString() {
        assertEquals(testStr, testLzrStr.asString())
    }

    @Test
    fun asArray() {
        assertContentEquals(intArrayOf(), testLzrStr.asArray())
    }

    @Test
    fun testHashCode() {
        assertEquals(97 * 3 + Objects.hashCode(testStr), testLzrStr.hashCode())
    }

    @Test
    fun testEquals() {
        assertEquals(true, LzrString(testStr) == LzrString(testStr))
        assertEquals(false, LzrString(testStr) == LzrString(""))
    }

    @Test
    fun compareToString() {
        val emptyStr = ""
        val emptyLzrStr = LzrString(emptyStr)
        assertEquals(testStr.compareTo(testStr), testLzrStr.compareTo(testLzrStr))
        assertEquals(emptyStr.compareTo(testStr), emptyLzrStr.compareTo(testLzrStr))
        assertEquals(testStr.compareTo(emptyStr), testLzrStr.compareTo(emptyLzrStr))
    }

    @Test
    fun compareToOther() {
        val other = Int.MAX_VALUE
        val otherLzr = LzrNumber(other)
        assertEquals(testStr.compareTo(other.toString()), testLzrStr.compareTo(otherLzr))
    }

    @Test
    fun testToString() {
        assertEquals(testLzrStr.asString(), testLzrStr.toString())
    }

    @Test
    fun emptyConst() {
        assertEquals(LzrString(""), LzrString.EMPTY)
    }

    private fun LzrValue.call(vararg args: LzrValue): LzrValue {
        return asLzrFunction().value.execute(*args)
    }
}