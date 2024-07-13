package runtime

import com.kingmang.lazurite.core.asLzrFunction
import com.kingmang.lazurite.parser.AST.Arguments
import com.kingmang.lazurite.parser.AST.ArgumentsBuilder
import com.kingmang.lazurite.parser.AST.Expressions.FunctionalExpression
import com.kingmang.lazurite.parser.AST.Expressions.ValueExpression
import com.kingmang.lazurite.parser.AST.Statements.ReturnStatement
import com.kingmang.lazurite.runtime.ClassInstanceBuilder
import com.kingmang.lazurite.runtime.values.LzrNumber
import com.kingmang.lazurite.runtime.values.LzrString
import org.junit.Test
import kotlin.test.assertEquals

class ClassInstanceBuilderTest {

    @Test
    fun build() {
        var isConstructorCalled = false
        val className = "testclass"
        val builder = ClassInstanceBuilder(className)
        builder.addField("field1", LzrString("field_value1"))
        builder.addMethod(
            "method1",
            Arguments(emptyList(), 0),
            ReturnStatement(ValueExpression(LzrString("method_value1")))
        )

        builder.addMethod(
            "toString",
            Arguments(emptyList(), 0),
            ReturnStatement(ValueExpression(LzrString("toString_value1")))
        )
        builder.addMethod(
            className,
            ArgumentsBuilder().build(),
            ReturnStatement(
                FunctionalExpression(
                    ValueExpression {
                        assertEquals(1, it.size)
                        assertEquals(LzrString("arg_value1"), it[0])
                        isConstructorCalled = true
                        LzrNumber.ZERO
                    },
                    listOf(ValueExpression("arg_value1"))
                )
            )
        )

        // in test, args not needed because FunctionalExpression provide args
        val instance = builder.build(arrayOf())

        assertEquals(LzrString("field_value1"), instance.access(LzrString("field1")))
        assertEquals(LzrString("method_value1"), instance.access(LzrString("method1")).asLzrFunction().value.execute())
        assertEquals(
            LzrString("toString_value1"),
            instance.access(LzrString("toString")).asLzrFunction().value.execute()
        )
        assertEquals(true, isConstructorCalled)
    }
}