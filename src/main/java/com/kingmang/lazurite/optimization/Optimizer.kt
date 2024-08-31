package com.kingmang.lazurite.optimization

import com.kingmang.lazurite.console.Console.print
import com.kingmang.lazurite.console.Console.println
import com.kingmang.lazurite.parser.ast.Node
import com.kingmang.lazurite.parser.ast.statements.Statement

object Optimizer {
    fun optimize(statement: Statement, level: Int, showSummary: Boolean): Statement {
        if (level == 0) return statement

        val optimization: Optimizable = SummaryOptimization(
            arrayOf(
                ConstantFolding(),
                ConstantPropagation(),
                DeadCodeElimination(),
                ExpressionSimplification(),
                InstructionCombining()
            )
        )

        var result: Node = statement
        if (level >= 9) {
            var iteration = 0
            var lastModifications = 0
            do {
                lastModifications = optimization.optimizationsCount()
                result = optimization.optimize(result)!!
                iteration++
            } while (lastModifications != optimization.optimizationsCount())
            if (showSummary) print("Performs $iteration optimization iterations")
        } else {
            for (i in 0 until level) {
                result = optimization.optimize(result)!!
            }
        }
        if (showSummary) {
            println(optimization.summaryInfo())
        }
        return result as Statement
    }
}
