package com.kingmang.lazurite.runtime;

import com.kingmang.lazurite.parser.AST.Arguments;
import com.kingmang.lazurite.parser.AST.Statements.Statement;
import com.kingmang.lazurite.runtime.values.LzrValue;
import org.jetbrains.annotations.NotNull;

public class ClassMethod extends UserDefinedFunction {
    
    public final ClassInstanceValue classInstance;
    
    public ClassMethod(Arguments arguments, Statement body, ClassInstanceValue classInstance) {
        super(arguments, body);
        this.classInstance = classInstance;
    }
    
    @Override
    public @NotNull LzrValue execute(@NotNull LzrValue... values) {
        Variables.push();
        Variables.define("this", classInstance.getThisMap());
        
        try {
            return super.execute(values);
        } finally {
            Variables.pop();
        }
    }
}
