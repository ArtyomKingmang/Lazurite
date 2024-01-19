package com.kingmang.lazurite.core;


import com.kingmang.lazurite.runtime.Value;

public interface Function {

    Value execute(Value... args);
}
