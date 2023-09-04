package com.kingmang.lazurite.libraries.GForms;


import com.kingmang.lazurite.base.*;
import com.kingmang.lazurite.runtime.FunctionValue;
import com.kingmang.lazurite.runtime.NumberValue;
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
        set("setColumnHeaderView", new FunctionValue(this::setColumnHeaderView));
        set("setCorner", new FunctionValue(this::setCorner));
        set("setHorizontalScrollBarPolicy", Converters.intToVoid(scrollPane::setHorizontalScrollBarPolicy));
        set("setRowHeaderView", new FunctionValue(this::setRowHeaderView));
        set("setVerticalScrollBarPolicy", Converters.intToVoid(scrollPane::setVerticalScrollBarPolicy));
        set("setViewportView", new FunctionValue(this::setViewportView));
        set("setWheelScrollingEnabled", Converters.booleanToVoid(scrollPane::setWheelScrollingEnabled));
    }
    
    private Value setViewportView(Value[] args) {
        Arguments.check(1, args.length);
        scrollPane.setViewportView(((ComponentValue) args[0]).component);
        return NumberValue.ZERO;
    }
    
    private Value setRowHeaderView(Value[] args) {
        Arguments.check(1, args.length);
        scrollPane.setRowHeaderView(((ComponentValue) args[0]).component);
        return NumberValue.ZERO;
    }
    
    private Value setColumnHeaderView(Value[] args) {
        Arguments.check(1, args.length);
        scrollPane.setColumnHeaderView(((ComponentValue) args[0]).component);
        return NumberValue.ZERO;
    }
    
    private Value setCorner(Value[] args) {
        Arguments.check(2, args.length);
        scrollPane.setCorner(args[0].asString(), ((ComponentValue) args[1]).component);
        return NumberValue.ZERO;
    }
}