package com.kingmang.lazurite.libraries.lzrx.awt.gforms;

import com.kingmang.lazurite.runtime.values.LzrMap;

import java.awt.*;

public class LayoutManagerValue extends LzrMap {

    final LayoutManager layout;

    public LayoutManagerValue(LayoutManager layout) {
        super(0);
        this.layout = layout;
    }
}