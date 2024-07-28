package parser.AST

import com.kingmang.lazurite.parser.ast.Argument
import com.kingmang.lazurite.parser.ast.ArgumentsBuilder
import com.kingmang.lazurite.parser.ast.expressions.ValueExpression
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ArgumentsBuilderTest {
    @Test
    fun build() {
        val expression1 = ValueExpression("value_optional1")
        val expression2 = ValueExpression("value_optional2")
        val expected = listOf(
            Argument("arg_required1"),
            Argument("arg_required2"),
            Argument("arg_optional1", expression1),
            Argument("arg_optional2", expression2),
        )
        val actual = ArgumentsBuilder().run {
            addRequired("arg_required1")
            addRequired("arg_required2")
            addOptional("arg_optional1", expression1)
            addOptional("arg_optional2", expression2)
            build()
        }
        assertEquals(4, actual.size(), "total size")
        assertEquals(2, actual.requiredCount, "required count")
        expected.forEachIndexed { index, argument ->
            assertEquals(argument, actual.get(index), "arg $index:${argument.name}")
        }
    }
}