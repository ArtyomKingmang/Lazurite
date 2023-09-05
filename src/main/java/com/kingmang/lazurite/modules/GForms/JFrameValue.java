package com.kingmang.lazurite.modules.GForms;

import com.kingmang.lazurite.base.Converters;

import javax.swing.JFrame;

public class JFrameValue extends WindowValue {

    final JFrame frame;

    public JFrameValue(JFrame frame) {
        super(9, frame);
        this.frame = frame;
        init();
    }

    private void init() {
        set("getTitle", Converters.voidToString(frame::getTitle));
        set("getResizable", Converters.voidToBoolean(frame::isResizable));
        set("getDefaultCloseOperation", Converters.voidToInt(frame::getDefaultCloseOperation));
        set("setDefaultCloseOperation", Converters.intToVoid(frame::setDefaultCloseOperation));
        set("setResizable", Converters.booleanOptToVoid(frame::setResizable));
        set("setTitle", Converters.stringToVoid(frame::setTitle));
    }
}