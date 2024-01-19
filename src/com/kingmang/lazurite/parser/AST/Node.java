package com.kingmang.lazurite.parser.AST;


import com.kingmang.lazurite.patterns.visitor.ResultVisitor;
import com.kingmang.lazurite.patterns.visitor.Visitor;

public interface Node {
    
    void accept(Visitor visitor);

    <R, T> R accept(ResultVisitor<R, T> visitor, T input);
}
