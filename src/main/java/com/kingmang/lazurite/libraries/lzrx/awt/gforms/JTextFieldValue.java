package com.kingmang.lazurite.libraries.lzrx.awt.gforms;


import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Converters;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.runtime.values.LzrFunction;
import com.kingmang.lazurite.runtime.values.LzrNumber;
import com.kingmang.lazurite.runtime.values.LzrValue;

import javax.swing.*;

import static com.kingmang.lazurite.utils.ValueUtils.consumeFunction;

public class JTextFieldValue extends JTextComponentValue {

    private final JTextField textField;

    public JTextFieldValue(JTextField textField) {
        super(10, textField);
        this.textField = textField;
        init();
    }

    private void init() {
        set("onAction", new LzrFunction(this::addActionListener));
        set("addActionListener", new LzrFunction(this::addActionListener));
        set("getColumns", Converters.voidToInt(textField::getColumns));
        set("getHorizontalAlignment", Converters.voidToInt(textField::getHorizontalAlignment));
        set("getScrollOffset", Converters.voidToInt(textField::getScrollOffset));
        set("postActionEvent", Converters.voidToVoid(textField::postActionEvent));
        set("setActionCommand", Converters.stringToVoid(textField::setActionCommand));
        set("setColumns", Converters.intToVoid(textField::setColumns));
        set("setHorizontalAlignment", Converters.intToVoid(textField::setHorizontalAlignment));
        set("setScrollOffset", Converters.intToVoid(textField::setScrollOffset));
    }

    private LzrValue addActionListener(LzrValue... args) {
        Arguments.check(1, args.length);
        Function action = consumeFunction(args[0], 1);
        textField.addActionListener(e -> action.execute());
        return LzrNumber.ZERO;
    }


}