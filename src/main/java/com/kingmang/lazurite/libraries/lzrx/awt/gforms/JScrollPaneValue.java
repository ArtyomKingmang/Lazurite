package com.kingmang.lazurite.libraries.lzrx.awt.gforms;


import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Converters;
import com.kingmang.lazurite.runtime.values.LzrFunction;
import com.kingmang.lazurite.runtime.values.LzrNumber;
import com.kingmang.lazurite.runtime.values.LzrValue;

import javax.swing.*;

public class JScrollPaneValue extends JComponentValue {

    final JScrollPane scrollPane;

    public JScrollPaneValue(JScrollPane scrollPane) {
        super(10, scrollPane);
        this.scrollPane = scrollPane;
        init();
    }

    private void init() {
        set("getHorizontalScrollBarPolicy", Converters.voidToInt(scrollPane::getHorizontalScrollBarPolicy));
        set("getVerticalScrollBarPolicy", Converters.voidToInt(scrollPane::getVerticalScrollBarPolicy));
        set("isWheelScrollingEnabled", Converters.voidToBoolean(scrollPane::isWheelScrollingEnabled));
        set("setColumnHeaderView", new LzrFunction(this::setColumnHeaderView));
        set("setCorner", new LzrFunction(this::setCorner));
        set("setHorizontalScrollBarPolicy", Converters.intToVoid(scrollPane::setHorizontalScrollBarPolicy));
        set("setRowHeaderView", new LzrFunction(this::setRowHeaderView));
        set("setVerticalScrollBarPolicy", Converters.intToVoid(scrollPane::setVerticalScrollBarPolicy));
        set("setViewportView", new LzrFunction(this::setViewportView));
        set("setWheelScrollingEnabled", Converters.booleanToVoid(scrollPane::setWheelScrollingEnabled));
    }
    
    private LzrValue setViewportView(LzrValue[] args) {
        Arguments.check(1, args.length);
        scrollPane.setViewportView(((ComponentValue) args[0]).component);
        return LzrNumber.ZERO;
    }
    
    private LzrValue setRowHeaderView(LzrValue[] args) {
        Arguments.check(1, args.length);
        scrollPane.setRowHeaderView(((ComponentValue) args[0]).component);
        return LzrNumber.ZERO;
    }
    
    private LzrValue setColumnHeaderView(LzrValue[] args) {
        Arguments.check(1, args.length);
        scrollPane.setColumnHeaderView(((ComponentValue) args[0]).component);
        return LzrNumber.ZERO;
    }
    
    private LzrValue setCorner(LzrValue[] args) {
        Arguments.check(2, args.length);
        scrollPane.setCorner(args[0].asString(), ((ComponentValue) args[1]).component);
        return LzrNumber.ZERO;
    }
}