package com.kingmang.lazurite.libraries.lzrx.gforms;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.runtime.values.LzrString;
import com.kingmang.lazurite.runtime.values.LzrValue;

import javax.swing.*;

public class Dialog implements Function {

    @Override
    public LzrValue execute(LzrValue... args) {
        final String v = JOptionPane.showInputDialog(args[0].asString());
        return new LzrString(v == null ? "0" : v);
    }
}
