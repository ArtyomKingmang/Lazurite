package com.kingmang.lazurite.libraries.lzrx.awt.gforms;


import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Converters;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.runtime.values.LzrFunction;
import com.kingmang.lazurite.runtime.values.LzrNumber;
import com.kingmang.lazurite.runtime.values.LzrValue;
import com.kingmang.lazurite.utils.ValueUtils;

import javax.swing.*;

public class AbstractButtonValue extends JComponentValue {

    final AbstractButton abstractButton;

    public AbstractButtonValue(int functionsCount, AbstractButton abstractButton) {
        super(functionsCount + 2, abstractButton);
        this.abstractButton = abstractButton;
        init();
    }

    private void init() {
        set("onClick", new LzrFunction(this::addActionListener));
        set("addActionListener", new LzrFunction(this::addActionListener));
        set("onChange", new LzrFunction(this::addChangeListener));
        set("addChangeListener", new LzrFunction(this::addChangeListener));
        set("doClick", Converters.intOptToVoid(abstractButton::doClick, abstractButton::doClick));
        set("getActionCommand", Converters.voidToString(abstractButton::getActionCommand));
        set("getDisplayedMnemonicIndex", Converters.voidToInt(abstractButton::getDisplayedMnemonicIndex));
        set("getHideActionText", Converters.voidToBoolean(abstractButton::getHideActionText));
        set("getHorizontalAlignment", Converters.voidToInt(abstractButton::getHorizontalAlignment));
        set("getHorizontalTextPosition", Converters.voidToInt(abstractButton::getHorizontalTextPosition));
        set("getIconTextGap", Converters.voidToInt(abstractButton::getIconTextGap));
        set("getText", Converters.voidToString(abstractButton::getText));
        set("getVerticalAlignment", Converters.voidToInt(abstractButton::getVerticalAlignment));
        set("getVerticalTextPosition", Converters.voidToInt(abstractButton::getVerticalTextPosition));
        set("isSelected", Converters.voidToBoolean(abstractButton::isSelected));
        set("setActionCommand", Converters.stringToVoid(abstractButton::setActionCommand));
        set("setHorizontalAlignment", Converters.intToVoid(abstractButton::setHorizontalAlignment));
        set("setHorizontalTextPosition", Converters.intToVoid(abstractButton::setHorizontalTextPosition));
        set("setSelected", Converters.booleanToVoid(abstractButton::setSelected));
        set("setText", Converters.stringToVoid(abstractButton::setText));
        set("setVerticalAlignment", Converters.intToVoid(abstractButton::setVerticalAlignment));
        set("setVerticalTextPosition", Converters.intToVoid(abstractButton::setVerticalTextPosition));
    }

    private LzrValue addActionListener(LzrValue[] args) {
        Arguments.check(1, args.length);
        final Function action = ValueUtils.consumeFunction(args[0], 0);
        abstractButton.addActionListener(e -> action.execute());
        return LzrNumber.ZERO;
    }
    
    private LzrValue addChangeListener(LzrValue[] args) {
        Arguments.check(1, args.length);
        final Function action = ValueUtils.consumeFunction(args[0], 0);
        abstractButton.addChangeListener(e -> action.execute());
        return LzrNumber.ZERO;
    }
}