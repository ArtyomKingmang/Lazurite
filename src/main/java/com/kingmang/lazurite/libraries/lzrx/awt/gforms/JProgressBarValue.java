package com.kingmang.lazurite.libraries.lzrx.awt.gforms;


import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Converters;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.runtime.values.LzrFunction;
import com.kingmang.lazurite.runtime.values.LzrNumber;
import com.kingmang.lazurite.runtime.values.LzrValue;
import com.kingmang.lazurite.utils.ValueUtils;

import javax.swing.*;

public class JProgressBarValue extends JComponentValue {

    final JProgressBar progressBar;

    public JProgressBarValue(JProgressBar progressBar) {
        super(19, progressBar);
        this.progressBar = progressBar;
        init();
    }

    private void init() {
        set("onChange", new LzrFunction(this::addChangeListener));
        set("addChangeListener", new LzrFunction(this::addChangeListener));
        set("getMinimum", Converters.voidToInt(progressBar::getMinimum));
        set("getMaximum", Converters.voidToInt(progressBar::getMaximum));
        set("getOrientation", Converters.voidToInt(progressBar::getOrientation));
        set("getValue", Converters.voidToInt(progressBar::getValue));
        set("isBorderPainted", Converters.voidToBoolean(progressBar::isBorderPainted));
        set("isIndeterminate", Converters.voidToBoolean(progressBar::isIndeterminate));
        set("isStringPainted", Converters.voidToBoolean(progressBar::isStringPainted));
        set("getString", Converters.voidToString(progressBar::getString));
        set("getPercentComplete", Converters.voidToDouble(progressBar::getPercentComplete));
        
        set("setMinimum", Converters.intToVoid(progressBar::setMinimum));
        set("setMaximum", Converters.intToVoid(progressBar::setMaximum));
        set("setBorderPainted", Converters.booleanToVoid(progressBar::setBorderPainted));
        set("setIndeterminate", Converters.booleanToVoid(progressBar::setIndeterminate));
        set("setOrientation", Converters.intToVoid(progressBar::setOrientation));
        set("setStringPainted", Converters.booleanToVoid(progressBar::setStringPainted));
        set("setString", Converters.stringToVoid(progressBar::setString));
        set("setValue", Converters.intToVoid(progressBar::setValue));
    }
    
    private LzrValue addChangeListener(LzrValue[] args) {
        Arguments.check(1, args.length);
        final Function action = ValueUtils.consumeFunction(args[0], 0);
        progressBar.addChangeListener(e -> action.execute());
        return LzrNumber.ZERO;
    }
}