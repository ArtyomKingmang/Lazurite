package utils

import com.kingmang.lazurite.utils.Log
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.FileReader
import kotlin.test.assertEquals

class LogTest {
    @BeforeEach
    fun before() {
        Log.clear()
    }

    @Test
    fun `write 2 lines Expect 2 lines appended`() {
        assertLogContent("")
        Log.append("line1")
        assertLogContent("line1\n")
        Log.append("line2")
        assertLogContent("line1\nline2\n")
    }

    @Test
    fun `clean after append Expect empty`() {
        assertLogContent("")
        Log.append("line1")
        Log.clear()
        assertLogContent("")
    }

    private fun assertLogContent(expected: String) {
        val actual = FileReader("log.txt").use {
            it.readText()
        }
        assertEquals(expected, actual)
    }
}