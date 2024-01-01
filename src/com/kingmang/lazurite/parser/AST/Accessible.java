package com.kingmang.lazurite.parser.AST;

import com.kingmang.lazurite.runtime.Value;

public interface Accessible extends Node {

    Value get();
    
    Value set(Value value);
}
