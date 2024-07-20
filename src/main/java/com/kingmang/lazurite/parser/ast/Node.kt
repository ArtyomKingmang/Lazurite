package com.kingmang.lazurite.parser.ast

import com.kingmang.lazurite.patterns.visitor.ResultVisitor
import com.kingmang.lazurite.patterns.visitor.Visitor

interface Node {
    fun accept(visitor: Visitor)

    fun <R, T> accept(visitor: ResultVisitor<R, T>, input: T): R?
}
