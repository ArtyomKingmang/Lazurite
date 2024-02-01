package com.kingmang.lazurite.libraries.gforms;


import com.kingmang.lazurite.core.*;
import com.kingmang.lazurite.runtime.Lzr.LzrFunction;
import com.kingmang.lazurite.runtime.Lzr.LzrNumber;
import com.kingmang.lazurite.runtime.Value;

import javax.swing.JScrollPane;

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
    
    private Value setViewportView(Value[] args) {
        Arguments.check(1, args.length);
        scrollPane.setViewportView(((com.kingmang.lazurite.libraries.gforms.ComponentValue) args[0]).component);
        return LzrNumber.ZERO;
    }
    
    private Value setRowHeaderView(Value[] args) {
        Arguments.check(1, args.length);
        scrollPane.setRowHeaderView(((com.kingmang.lazurite.libraries.gforms.ComponentValue) args[0]).component);
        return LzrNumber.ZERO;
    }
    
    private Value setColumnHeaderView(Value[] args) {
        Arguments.check(1, args.length);
        scrollPane.setColumnHeaderView(((com.kingmang.lazurite.libraries.gforms.ComponentValue) args[0]).component);
        return LzrNumber.ZERO;
    }
    
    private Value setCorner(Value[] args) {
        Arguments.check(2, args.length);
        scrollPane.setCorner(args[0].asString(), ((ComponentValue) args[1]).component);
        return LzrNumber.ZERO;
    }
}