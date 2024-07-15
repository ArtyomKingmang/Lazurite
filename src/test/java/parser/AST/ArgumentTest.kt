package parser.AST

import com.kingmang.lazurite.parser.AST.Argument
import com.kingmang.lazurite.parser.AST.Expressions.ValueExpression
import org.junit.Test
import kotlin.test.assertEquals

class ArgumentTest {

    @Test
    fun testToStringNoExpression() {
        val arg = Argument("arg")

        assertEquals("arg", arg.toString())
    }

    @Test
    fun testToStringWithExpression() {
        val expression = ValueExpression("value")
        val arg = Argument("arg", expression)

        assertEquals("arg = $expression", arg.toString())
    }
}