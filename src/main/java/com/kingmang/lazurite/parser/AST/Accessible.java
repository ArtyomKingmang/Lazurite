package com.kingmang.lazurite.parser.AST;

import com.kingmang.lazurite.runtime.values.LzrValue;

public interface Accessible extends Node {

    LzrValue get();
    
    LzrValue set(LzrValue value);
}
