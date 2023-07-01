package com.kingmang.lazurite.parser.ast;

import com.kingmang.lazurite.runtime.Value;


public interface Expression extends Node {
    
    Value eval();
}
