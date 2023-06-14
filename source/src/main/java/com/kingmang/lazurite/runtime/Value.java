package com.kingmang.lazurite.runtime;

/**
 *
 * @author aNNiMON
 */
public interface Value extends Comparable<Value> {
    
    Object raw();
    
    int asInt();
    
    double asNumber();
    
    String asString();
    
    int type();
}
