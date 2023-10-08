package com.kingmang.lazurite.runtime;

import javax.imageio.plugins.jpeg.JPEGHuffmanTable;

public interface Value extends Comparable<Value> {
    
    Object raw();
    
    int asInt();

    double asNumber();
    
    String asString();

    int[] asArray();

    int type();

}
