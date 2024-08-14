package com.kingmang.lazurite.parser.ast.expressions

import com.kingmang.lazurite.core.ClassDeclarations
import com.kingmang.lazurite.core.Instantiable
import com.kingmang.lazurite.exceptions.LzrException
import com.kingmang.lazurite.patterns.visitor.ResultVisitor
import com.kingmang.lazurite.patterns.visitor.Visitor
import com.kingmang.lazurite.runtime.ClassInstanceBuilder
import com.kingmang.lazurite.runtime.Variables.get
import com.kingmang.lazurite.runtime.Variables.isExists
import com.kingmang.lazurite.runtime.values.LzrValue

data class ObjectCreationExpression(
    val className: String,
    val constructorArguments: List<Expression>
) : Expression {
    override fun eval(): LzrValue {
        val cd = ClassDeclarations[className]
        if (cd == null) {
            // Is Instantiable?
            if (isExists(className)) {
                val variable = get(className)
                if (variable is Instantiable) {
                    return variable.newInstance(ctorArgs())
                }
            }
            throw LzrException("UnknownClassException", "Unknown class $className")
        }


        val builder = ClassInstanceBuilder(className)
        for (f in cd.fields) {
            val fieldName = (f.target as VariableExpression).name
            builder.addField(fieldName, f.eval())
        }
        for (m in cd.methods) {
            builder.addMethod(m.name, m.arguments, m.body)
        }
        return builder.build(ctorArgs())
    }

    private fun ctorArgs(): Array<LzrValue> =
        constructorArguments.map(Expression::eval).toTypedArray()

    override fun accept(visitor: Visitor) =
        visitor.visit(this)

    override fun <R, T> accept(visitor: ResultVisitor<R, T>, input: T): R? =
        visitor.visit(this, input)

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("new ").append(className).append(' ')
        val it = constructorArguments.iterator()
        if (it.hasNext()) {
            sb.append(it.next())
            while (it.hasNext()) {
                sb.append(", ").append(it.next())
            }
        }
        sb.append(')')
        return sb.toString()
    }
}
