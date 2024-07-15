package parser.AST

import com.kingmang.lazurite.parser.AST.Argument
import com.kingmang.lazurite.parser.AST.ArgumentsBuilder
import com.kingmang.lazurite.parser.AST.Expressions.ValueExpression
import org.junit.Test
import kotlin.test.assertEquals

class ArgumentsBuilderTest {

    @Test
    fun build() {
        val expression = ValueExpression("value_optional")
        val expected = listOf(
            Argument("arg_required1"),
            Argument("arg_required2"),
            Argument("arg_optional1", expression),
            Argument("arg_optional2"),
        )
        val actual = ArgumentsBuilder().run {
            addRequired("arg_required1")
            addRequired("arg_required2")
            addOptional("arg_optional1", expression)
            addOptional("arg_optional2", null)
            build()
        }
        assertEquals(4, actual.size(), "total size")
        assertEquals(2, actual.requiredCount, "required count")
        expected.forEachIndexed { index, argument ->
            assertEquals(argument, actual.get(index), "arg $index:${argument.name}")
        }
    }
}