package com.kingmang.lazurite.libraries.lzrx.awt.gforms;

import com.kingmang.lazurite.core.Converters;

import javax.swing.*;

public class JLabelValue extends JComponentValue {

    final JLabel label;

    public JLabelValue(JLabel label) {
        super(17, label);
        this.label = label;
        init();
    }

    private void init() {
        set("getDisplayedMnemonic", Converters.voidToInt(label::getDisplayedMnemonic));
        set("getDisplayedMnemonicIndex", Converters.voidToInt(label::getDisplayedMnemonicIndex));
        set("getHorizontalAlignment", Converters.voidToInt(label::getHorizontalAlignment));
        set("getHorizontalTextPosition", Converters.voidToInt(label::getHorizontalTextPosition));
        set("getIconTextGap", Converters.voidToInt(label::getIconTextGap));
        set("getVerticalAlignment", Converters.voidToInt(label::getVerticalAlignment));
        set("getVerticalTextPosition", Converters.voidToInt(label::getVerticalTextPosition));

        set("getText", Converters.voidToString(label::getText));
        set("setDisplayedMnemonic", Converters.intToVoid(label::setDisplayedMnemonic));
        set("setDisplayedMnemonicIndex", Converters.intToVoid(label::setDisplayedMnemonicIndex));
        set("setHorizontalAlignment", Converters.intToVoid(label::setHorizontalAlignment));
        set("setHorizontalTextPosition", Converters.intToVoid(label::setHorizontalTextPosition));
        set("setIconTextGap", Converters.intToVoid(label::setIconTextGap));
        set("setVerticalAlignment", Converters.intToVoid(label::setVerticalAlignment));
        set("setVerticalTextPosition", Converters.intToVoid(label::setVerticalTextPosition));
        set("setText", Converters.stringToVoid(label::setText));
    }
}