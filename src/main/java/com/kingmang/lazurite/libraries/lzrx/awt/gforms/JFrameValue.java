package com.kingmang.lazurite.libraries.lzrx.awt.gforms;

import com.github.weisj.darklaf.LafManager;
import com.kingmang.lazurite.core.Converters;

import javax.swing.*;

public class JFrameValue extends FrameValue {

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