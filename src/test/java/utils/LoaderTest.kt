package utils

import com.kingmang.lazurite.utils.Loader
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class LoaderTest {
    @Test
    fun `loadSource with resource Expect success`() {
        val actual = Loader.readSource("unit-test-res.lzr")
        assertEquals("print(\"unit-test-res\")", actual)
    }

    @Test
    fun `loadSource with file Expect success`() {
        val actual = Loader.readSource("test/unit-test-file.lzr")
        assertEquals("print(\"unit-test-file\")", actual)
    }

    @Test
    fun `loadSource with unknown Expect fail`() {
        assertFails {
            Loader.readSource("someunknownfile.hello")
        }
    }
}