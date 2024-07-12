package runtime.scope

import com.kingmang.lazurite.runtime.scope.Scope
import com.kingmang.lazurite.runtime.scope.findOrCurrent
import org.junit.Test
import kotlin.test.assertEquals

class ScopeTest {

    companion object {
        private const val DATA = "root_data"
    }

    @Test
    fun `find in empty root Expect root and notfound`() {
        val root = Scope(null, setOf<String>())
        val result = root.findOrCurrent { it.data.contains(DATA) }
        assertEquals(root, result.scope)
        assertEquals(false, result.isFound)
    }

    @Test
    fun `find in filled root Expect root and found`() {
        val root = Scope(null, setOf(DATA))
        val result = root.findOrCurrent { it.data.contains(DATA) }
        assertEquals(root, result.scope)
        assertEquals(true, result.isFound)
    }

    @Test
    fun `find in empty root and empty child Expect child and notfound`() {
        val root = Scope(null, setOf<String>())
        val child = Scope(root, setOf())
        val result = child.findOrCurrent { it.data.contains(DATA) }
        assertEquals(child, result.scope)
        assertEquals(false, result.isFound)
    }

    @Test
    fun `find in empty root and filled child Expect child and found`() {
        val root = Scope(null, setOf<String>())
        val child = Scope(root, setOf(DATA))
        val result = child.findOrCurrent { it.data.contains(DATA) }
        assertEquals(child, result.scope)
        assertEquals(true, result.isFound)
    }

    @Test
    fun `find in filled root and empty child Expect root and found`() {
        val root = Scope(null, setOf(DATA))
        val child = Scope(root, setOf())
        val result = child.findOrCurrent { it.data.contains(DATA) }
        assertEquals(root, result.scope)
        assertEquals(true, result.isFound)
    }

    @Test
    fun `find in filled root and filled child Expect child and found`() {
        val root = Scope(null, setOf(DATA))
        val child = Scope(root, setOf(DATA))
        val result = child.findOrCurrent { it.data.contains(DATA) }
        assertEquals(child, result.scope)
        assertEquals(true, result.isFound)
    }

}