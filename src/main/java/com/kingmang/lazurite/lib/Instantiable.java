package com.kingmang.lazurite.lib;


import com.kingmang.lazurite.runtime.Value;

public interface Instantiable {
    
    Value newInstance(Value[] args);
}
