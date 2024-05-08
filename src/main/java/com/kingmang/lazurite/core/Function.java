package com.kingmang.lazurite.core;

import com.kingmang.lazurite.runtime.LzrValue;

@FunctionalInterface
public interface Function {
    LzrValue execute(LzrValue... args);
}
