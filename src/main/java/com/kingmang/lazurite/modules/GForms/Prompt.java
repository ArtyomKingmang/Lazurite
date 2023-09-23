package com.kingmang.lazurite.modules.GForms;

import com.kingmang.lazurite.base.Function;
import com.kingmang.lazurite.runtime.LZR.LZRString;
import com.kingmang.lazurite.runtime.Value;

import javax.swing.*;

public class Prompt implements Function {

    @Override
    public Value execute(Value... args) {
        final String v = JOptionPane.showInputDialog(args[0].asString());
        return new LZRString(v == null ? "0" : v);
    }
}
