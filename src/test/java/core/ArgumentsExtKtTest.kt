package core

import com.kingmang.lazurite.core.check
import com.kingmang.lazurite.core.checkAtLeast
import com.kingmang.lazurite.core.checkOrOr
import com.kingmang.lazurite.core.checkRange
import com.kingmang.lazurite.exceptions.LzrException
import com.kingmang.lazurite.runtime.values.LzrNumber
import com.kingmang.lazurite.runtime.values.LzrValue
import org.junit.jupiter.api.Test
import testutils.assertLzrFails

class ArgumentsExtKtTest {
    @Test
    fun check() {
        val data = arrayOf(LzrNumber.MINUS_ONE, LzrNumber.ZERO, LzrNumber.ONE)
        data.check(3)
        assertLzrFails(mismatchException("1 argument expected, got 3")) {
            data.check(1)
        }
        assertLzrFails(mismatchException("2 arguments expected, got 3")) {
            data.check(2)
        }
    }

    @Test
    fun checkAtLeast() {
        val data = arrayOf<LzrValue>()
        data.checkAtLeast(0)
        assertLzrFails(mismatchException("At least 1 argument expected, got 0")) {
            data.checkAtLeast(1)
        }
        assertLzrFails(mismatchException("At least 2 arguments expected, got 0")) {
            data.checkAtLeast(2)
        }
    }

    @Test
    fun checkOrOr() {
        val data = arrayOf(LzrNumber.MINUS_ONE, LzrNumber.ZERO, LzrNumber.ONE)
        data.checkOrOr(3, 3)
        data.checkOrOr(3, 1)
        data.checkOrOr(1, 3)
        assertLzrFails(mismatchException("1 or 1 arguments expected, got 3")) {
            data.checkOrOr(1, 1)
        }
    }

    @Test
    fun checkRange() {
        val data = arrayOf(LzrNumber.MINUS_ONE, LzrNumber.ZERO, LzrNumber.ONE)
        data.checkRange(0, 3)
        data.checkRange(1, 3)
        data.checkRange(2, 3)
        data.checkRange(3, 3)
        assertLzrFails(mismatchException("From 0 to 2 arguments expected, got 3")) {
            data.checkRange(0, 2)
        }
    }

    private fun mismatchException(text: String): LzrException {
        return LzrException("ArgumentsMismatchException", text)
    }
}