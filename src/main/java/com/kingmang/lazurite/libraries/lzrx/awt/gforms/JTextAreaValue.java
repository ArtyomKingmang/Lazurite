package com.kingmang.lazurite.libraries.lzrx.awt.gforms;


import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Converters;
import com.kingmang.lazurite.runtime.values.LzrFunction;
import com.kingmang.lazurite.runtime.values.LzrNumber;
import com.kingmang.lazurite.runtime.values.LzrValue;

import javax.swing.*;
import javax.swing.text.BadLocationException;

public class JTextAreaValue extends JTextComponentValue {

    private final JTextArea textArea;

    public JTextAreaValue(JTextArea textArea) {
        super(18, textArea);
        this.textArea = textArea;
        init();
    }

    private void init() {
        set("append", Converters.stringToVoid(textArea::append));
        set("getColumns", Converters.voidToInt(textArea::getColumns));
        set("getLineCount", Converters.voidToInt(textArea::getLineCount));
        set("getLineStartOffset", offsetFunction(textArea::getLineStartOffset));
        set("getLineEndOffset", offsetFunction(textArea::getLineEndOffset));
        set("getLineOfOffset", offsetFunction(textArea::getLineOfOffset));
        set("getLineWrap", Converters.voidToBoolean(textArea::getLineWrap));
        set("getWrapStyleWord", Converters.voidToBoolean(textArea::getWrapStyleWord));
        set("getRows", Converters.voidToInt(textArea::getRows));
        set("getColumns", Converters.voidToInt(textArea::getColumns));
        set("getTabSize", Converters.voidToInt(textArea::getTabSize));
        set("insert", this::insert);
        set("setRows", Converters.intToVoid(textArea::setRows));
        set("setColumns", Converters.intToVoid(textArea::setColumns));
        set("setTabSize", Converters.intToVoid(textArea::setTabSize));
        set("setLineWrap", Converters.booleanToVoid(textArea::setLineWrap));
        set("setWrapStyleWord", Converters.booleanToVoid(textArea::setWrapStyleWord));
    }
    
    private LzrValue insert(LzrValue[] args) {
        Arguments.check(2, args.length);
        textArea.insert(args[0].asString(), args[1].asInt());
        return LzrNumber.ZERO;
    }
    
    private interface OffsetFunction {
        int accept(int line) throws BadLocationException;
    }
    
    private LzrFunction offsetFunction(OffsetFunction f) {
        return new LzrFunction(args -> {
            Arguments.check(1, args.length);
            try {
                int result = f.accept(args[0].asInt());
                return LzrNumber.of(result);
            } catch (BadLocationException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}