package com.kingmang.lazurite.libraries.GForms;

import com.kingmang.lazurite.base.Function;
import com.kingmang.lazurite.runtime.StringValue;
import com.kingmang.lazurite.runtime.Value;

import javax.swing.*;

public class Dialog implements Function {

    @Override
    public Value execute(Value... args) {
        final String v = JOptionPane.showInputDialog(args[0].asString());
        return new StringValue(v == null ? "0" : v);
    }
}
