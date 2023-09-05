package com.kingmang.lazurite.modules.GForms;

import com.kingmang.lazurite.runtime.MapValue;

import java.awt.LayoutManager;

public class LayoutManagerValue extends MapValue {

    final LayoutManager layout;

    public LayoutManagerValue(LayoutManager layout) {
        super(0);
        this.layout = layout;
    }
}