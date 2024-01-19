package com.kingmang.lazurite.core;


import com.kingmang.lazurite.runtime.Value;

public interface Instantiable {
    
    Value newInstance(Value[] args);
}
