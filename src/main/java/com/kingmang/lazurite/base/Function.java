package com.kingmang.lazurite.base;


import com.kingmang.lazurite.runtime.Value;

public interface Function {

    Value execute(Value... args);
}
