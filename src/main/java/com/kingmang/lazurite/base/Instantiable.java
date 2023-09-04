package com.kingmang.lazurite.base;


import com.kingmang.lazurite.runtime.Value;

public interface Instantiable {
    
    Value newInstance(Value[] args);
}
