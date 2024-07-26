package parser.AST

import com.kingmang.lazurite.parser.ast.Argument
import com.kingmang.lazurite.parser.ast.Arguments
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ArgumentsTest {
    private val argsList = buildList { repeat(10) { add(Argument("arg$it")) } }
    private val args = Arguments(argsList, 5)

    @Test
    fun get() {
        argsList.forEachIndexed { index, argument ->
            assertEquals(argument, args.get(index))
        }
    }

    @Test
    fun size() {
        assertEquals(10, args.size())
    }

    @Test
    fun iterator() {
        val actual = args.iterator().asSequence().toList()
        assertEquals(argsList, actual)
    }

    @Test
    fun testToString() {
        val expected = argsList.joinToString(separator = ", ", prefix = "(", postfix = ")")
        assertEquals(expected, args.toString())
    }

    @Test
    fun getRequiredCount() {
        assertEquals(5, args.requiredCount)
    }
}