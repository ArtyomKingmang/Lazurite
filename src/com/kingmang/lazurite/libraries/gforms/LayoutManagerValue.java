package com.kingmang.lazurite.libraries.gforms;

import com.kingmang.lazurite.runtime.LZR.LZRMap;

import java.awt.LayoutManager;

public class LayoutManagerValue extends LZRMap {

    final LayoutManager layout;

    public LayoutManagerValue(LayoutManager layout) {
        super(0);
        this.layout = layout;
    }
}