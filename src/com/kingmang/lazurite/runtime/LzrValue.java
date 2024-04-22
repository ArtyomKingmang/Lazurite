package com.kingmang.lazurite.runtime;

public interface LzrValue extends Comparable<LzrValue> {
    
    Object raw();
    
    int asInt();

    double asNumber();
    
    String asString();

    int[] asArray();

    int type();

}
