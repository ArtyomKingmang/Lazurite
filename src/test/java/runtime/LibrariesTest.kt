package runtime

import com.kingmang.lazurite.runtime.Libraries
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class LibrariesTest {
    companion object {
        private const val LIB1 = "lib1"
        private const val LIB2 = "lib2"
        private const val LIB3 = "lib3"
    }

    @BeforeEach
    fun resetLibrariesState() {
        Libraries.clear()
    }

    @Test
    fun `initial state Expect not exists`() {
        val result = Libraries.isExists(LIB1)
        assertEquals(false, result)
    }

    @Test
    fun `add lib Expect exists`() {
        Libraries.add(LIB1)
        assertEquals(true, Libraries.isExists(LIB1))
    }

    @Test
    fun `remove lib Expect not exists`() {
        Libraries.remove(LIB1)
        assertEquals(false, Libraries.isExists(LIB1))
    }

    @Test
    fun `add lib and remove lib Expect not exists`() {
        Libraries.add(LIB1)
        Libraries.remove(LIB1)
        assertEquals(false, Libraries.isExists(LIB1))
    }

    @Test
    fun `add lib and clear Expect not exists`() {
        Libraries.add(LIB1)
        Libraries.clear()
        assertEquals(false, Libraries.isExists(LIB1))
    }

    @Test
    fun `add lib and push Expect exists`() {
        Libraries.add(LIB1)
        Libraries.push()
        assertEquals(true, Libraries.isExists(LIB1))
    }

    @Test
    fun `add lib and push and pop Expect exists`() {
        Libraries.add(LIB1)
        Libraries.push()
        Libraries.pop()
        assertEquals(true, Libraries.isExists(LIB1))
    }

    @Test
    fun `add lib and push and remove lib Expect not exists`() {
        Libraries.add(LIB1)
        Libraries.push()
        Libraries.remove(LIB1)
        assertEquals(false, Libraries.isExists(LIB1))
    }

    @Test
    fun `pop Expect not exists`() {
        Libraries.pop()
        assertEquals(false, Libraries.isExists(LIB1))
    }

    @Test
    fun `push Expect not exists`() {
        Libraries.push()
        assertEquals(false, Libraries.isExists(LIB1))
    }

    @Test
    fun `push and add lib Expect exists`() {
        Libraries.push()
        Libraries.add(LIB1)
        assertEquals(true, Libraries.isExists(LIB1))
    }

    @Test
    fun `push and add lib and remove lib Expect not exists`() {
        Libraries.push()
        Libraries.add(LIB1)
        Libraries.remove(LIB1)
        assertEquals(false, Libraries.isExists(LIB1))
    }

    @Test
    fun `push and add lib and clear Expect not exists`() {
        Libraries.push()
        Libraries.add(LIB1)
        Libraries.clear()
        assertEquals(false, Libraries.isExists(LIB1))
    }

    @Test
    fun `push and add lib and pop Expect not exists`() {
        Libraries.push()
        Libraries.add(LIB1)
        Libraries.pop()
        assertEquals(false, Libraries.isExists(LIB1))
    }

    @Test
    fun `add list of libs Expect exists all`() {
        Libraries.add(LIB1)
        Libraries.push()
        Libraries.add(LIB2)
        Libraries.push()
        Libraries.add(LIB3)
        Libraries.push()
        assertEquals(true, Libraries.isExists(LIB1))
        assertEquals(true, Libraries.isExists(LIB2))
        assertEquals(true, Libraries.isExists(LIB3))
    }

    @Test
    fun `add list of libs and remove lib2 Expect exists all Except lib2`() {
        Libraries.add(LIB1)
        Libraries.push()
        Libraries.add(LIB2)
        Libraries.push()
        Libraries.add(LIB3)
        Libraries.push()
        Libraries.remove(LIB2)
        assertEquals(true, Libraries.isExists(LIB1))
        assertEquals(false, Libraries.isExists(LIB2))
        assertEquals(true, Libraries.isExists(LIB3))
    }

}