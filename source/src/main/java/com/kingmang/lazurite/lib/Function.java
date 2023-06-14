package com.kingmang.lazurite.lib;


import com.kingmang.lazurite.runtime.Value;

public interface Function {

    Value execute(Value... args);
}
