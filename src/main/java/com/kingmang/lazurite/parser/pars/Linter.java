package com.kingmang.lazurite.parser.pars;

import com.kingmang.lazurite.parser.linters.AssignValidator;
import com.kingmang.lazurite.parser.linters.UsingWithNonStringValueValidator;
import com.kingmang.lazurite.parser.linters.DefaultFunctionsOverrideValidator;
import com.kingmang.lazurite.base.KEYWORD;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.parser.ast.Statement;
import com.kingmang.lazurite.parser.ast.Visitor;

public final class Linter {

    public static void lint(Statement program) {
        new Linter(program).execute();
    }

    private final Statement program;

    private Linter(Statement program) {
        this.program = program;
    }
    
    public void execute() {
        final Visitor[] validators = new Visitor[] {
            new UsingWithNonStringValueValidator(),
            new AssignValidator(),
            new DefaultFunctionsOverrideValidator()
        };
        resetState();
        for (Visitor validator : validators) {
            program.accept(validator);
            resetState();
        }
        Console.println("Lint validation complete!");
    }

    private void resetState() {
        Variables.clear();
        KEYWORD.getFunctions().clear();
    }
}
