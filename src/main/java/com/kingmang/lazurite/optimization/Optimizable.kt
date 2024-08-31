package com.kingmang.lazurite.optimization

import com.kingmang.lazurite.parser.ast.Node


interface Optimizable {
    fun optimize(node: Node?): Node?

    fun optimizationsCount(): Int

    fun summaryInfo(): String?
}
