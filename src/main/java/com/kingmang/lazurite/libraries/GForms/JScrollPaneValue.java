package com.kingmang.lazurite.libraries.GForms;


import com.kingmang.lazurite.core.*;
import com.kingmang.lazurite.runtime.LZR.LZRFunction;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
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
        set("setColumnHeaderView", new LZRFunction(this::setColumnHeaderView));
        set("setCorner", new LZRFunction(this::setCorner));
        set("setHorizontalScrollBarPolicy", Converters.intToVoid(scrollPane::setHorizontalScrollBarPolicy));
        set("setRowHeaderView", new LZRFunction(this::setRowHeaderView));
        set("setVerticalScrollBarPolicy", Converters.intToVoid(scrollPane::setVerticalScrollBarPolicy));
        set("setViewportView", new LZRFunction(this::setViewportView));
        set("setWheelScrollingEnabled", Converters.booleanToVoid(scrollPane::setWheelScrollingEnabled));
    }
    
    private Value setViewportView(Value[] args) {
        Arguments.check(1, args.length);
        scrollPane.setViewportView(((ComponentValue) args[0]).component);
        return LZRNumber.ZERO;
    }
    
    private Value setRowHeaderView(Value[] args) {
        Arguments.check(1, args.length);
        scrollPane.setRowHeaderView(((ComponentValue) args[0]).component);
        return LZRNumber.ZERO;
    }
    
    private Value setColumnHeaderView(Value[] args) {
        Arguments.check(1, args.length);
        scrollPane.setColumnHeaderView(((ComponentValue) args[0]).component);
        return LZRNumber.ZERO;
    }
    
    private Value setCorner(Value[] args) {
        Arguments.check(2, args.length);
        scrollPane.setCorner(args[0].asString(), ((ComponentValue) args[1]).component);
        return LZRNumber.ZERO;
    }
}