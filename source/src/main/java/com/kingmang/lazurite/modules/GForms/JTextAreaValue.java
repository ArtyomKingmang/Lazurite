package com.kingmang.lazurite.modules.GForms;


import com.kingmang.lazurite.lib.*;
import com.kingmang.lazurite.runtime.FunctionValue;
import com.kingmang.lazurite.runtime.NumberValue;
import com.kingmang.lazurite.runtime.Value;

import javax.swing.JTextArea;
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
    
    private Value insert(Value[] args) {
        Arguments.check(2, args.length);
        textArea.insert(args[0].asString(), args[1].asInt());
        return NumberValue.ZERO;
    }
    
    private interface OffsetFunction {
        int accept(int line) throws BadLocationException;
    }
    
    private FunctionValue offsetFunction(OffsetFunction f) {
        return new FunctionValue(args -> {
            Arguments.check(1, args.length);
            try {
                int result = f.accept(args[0].asInt());
                return NumberValue.of(result);
            } catch (BadLocationException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}