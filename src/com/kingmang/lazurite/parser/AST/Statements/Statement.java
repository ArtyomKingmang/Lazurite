package com.kingmang.lazurite.parser.AST.Statements;


import com.kingmang.lazurite.parser.AST.Node;

public interface Statement extends Node {
    
    void execute();
}
