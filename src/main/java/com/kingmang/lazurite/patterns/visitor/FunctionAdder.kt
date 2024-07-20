package com.kingmang.lazurite.patterns.visitor

import com.kingmang.lazurite.parser.ast.statements.FunctionDefineStatement

class FunctionAdder : AbstractVisitor() {
    override fun visit(s: FunctionDefineStatement) {
        super.visit(s)
        s.execute()
    }
}