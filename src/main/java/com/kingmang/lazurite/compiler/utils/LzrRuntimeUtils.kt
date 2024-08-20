package com.kingmang.lazurite.compiler.utils

import com.kingmang.lazurite.libraries.Keyword
import com.kingmang.lazurite.parser.ast.expressions.ValueExpression
import com.kingmang.lazurite.parser.ast.statements.UsingStatement
import com.kingmang.lazurite.runtime.Libraries
import com.kingmang.lazurite.runtime.Variables
import ru.DmN.pht.jvm.utils.vtype.DynamicReturn

object LzrRuntimeUtils {
    @JvmStatic
    fun pushVariables(): Unit =
        Variables.push()

    @JvmStatic
    fun popVariables(): Unit =
        Variables.pop()

    @JvmStatic
    fun defSetVariable(name: String, value: Any?): Unit =
        Variables.set(name, LzrObjectUtils.castToLzrValue(value))

    @JvmStatic
    @Synchronized
    @DynamicReturn
    fun getVariable(name: String): Any? {
        val scopeData = Variables.findScope(name)
        if (scopeData.isFound)
            return scopeData.scope.data[name]?.raw()
        if (Keyword.isExists(name))
            return Keyword.get(name)
        val instance = getVariableOrNull(name)
        if (instance != null)
            return LzrObjectUtils.getField(instance, name)
        val `this` = getVariableOrNull("this")
        if (`this` != null)
            return LzrObjectUtils.getField(`this`, name)
        throw RuntimeException("Variable '$name' not found!")
    }

    @JvmStatic
    @Synchronized
    @DynamicReturn
    fun getVariableOrNull(name: String): Any? {
        val scopeData = Variables.findScope(name)
        if (scopeData.isFound)
            return scopeData.scope.data[name]?.raw()
        return null
    }

    @JvmStatic
    @Synchronized
    fun loadLibrary(name: String) {
        if (Libraries.isExists(name))
            return
        UsingStatement(ValueExpression(name)).execute()
    }
}