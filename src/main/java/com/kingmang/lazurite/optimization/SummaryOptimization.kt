package com.kingmang.lazurite.optimization

import com.kingmang.lazurite.parser.ast.Node


class SummaryOptimization(private val optimizations: Array<Optimizable>) : Optimizable {
    override fun optimize(node: Node?): Node? {
        var node = node
        for (optimization in optimizations) {
            node = optimization.optimize(node)
        }
        return node
    }

    override fun optimizationsCount(): Int {
        var count = 0
        for (optimization in optimizations) {
            count += optimization.optimizationsCount()
        }
        return count
    }

    override fun summaryInfo(): String {
        val sb = StringBuilder()
        for (optimization in optimizations) {
            sb.append(optimization.summaryInfo())
        }
        return sb.toString()
    }
}
