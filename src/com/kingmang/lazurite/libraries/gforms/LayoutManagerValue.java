package com.kingmang.lazurite.libraries.gforms;

import com.kingmang.lazurite.runtime.Types.LzrMap;

import java.awt.LayoutManager;

public class LayoutManagerValue extends LzrMap {

    final LayoutManager layout;

    public LayoutManagerValue(LayoutManager layout) {
        super(0);
        this.layout = layout;
    }
}