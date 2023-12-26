package com.kingmang.lazurite.parser.pars;

import com.kingmang.lazurite.parser.ast.Node;
import com.kingmang.lazurite.parser.ast.Statement;
import com.kingmang.lazurite.parser.optimization.ConstantFolding;
import com.kingmang.lazurite.parser.optimization.ConstantPropagation;
import com.kingmang.lazurite.parser.optimization.DeadCodeElimination;
import com.kingmang.lazurite.parser.optimization.ExpressionSimplification;
import com.kingmang.lazurite.parser.optimization.InstructionCombining;
import com.kingmang.lazurite.parser.optimization.Optimizable;
import com.kingmang.lazurite.parser.optimization.SummaryOptimization;

public final class Optimizer {

    private Optimizer() { }

    public static Statement optimize(Statement statement, int level, boolean showSummary) {
        if (level == 0) return statement;

        final Optimizable optimization = new SummaryOptimization(new Optimizable[] {
            new ConstantFolding(),
            new ConstantPropagation(),
            new DeadCodeElimination(),
            new ExpressionSimplification(),
            new InstructionCombining()
        });

        Node result = statement;
        if (level >= 9) {
            int iteration = 0, lastModifications = 0;
            do {
                lastModifications = optimization.optimizationsCount();
                result = optimization.optimize(result);
                iteration++;
            } while (lastModifications != optimization.optimizationsCount());
            if (showSummary)
                Console.print("Performs " + iteration + " optimization iterations");
        } else {
            for (int i = 0; i < level; i++) {
                result = optimization.optimize(result);
            }
        }
        if (showSummary) {
            Console.println(optimization.summaryInfo());
        }
        return (Statement) result;
    }
}
