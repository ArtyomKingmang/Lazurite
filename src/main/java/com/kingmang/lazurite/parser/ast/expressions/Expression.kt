package com.kingmang.lazurite.parser.ast.expressions

import com.kingmang.lazurite.parser.ast.Node
import com.kingmang.lazurite.runtime.values.LzrValue

interface Expression : Node {
    /**
     * If u need return "null" use "LzrNull".
     */
    fun eval(): LzrValue
}