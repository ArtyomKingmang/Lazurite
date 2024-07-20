package com.kingmang.lazurite.parser.ast.statements

import com.kingmang.lazurite.parser.ast.Node

interface Statement : Node {
    fun execute()
}
