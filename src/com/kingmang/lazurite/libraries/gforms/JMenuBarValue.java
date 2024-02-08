package com.kingmang.lazurite.libraries.gforms;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.runtime.Lzr.LzrFunction;
import com.kingmang.lazurite.runtime.Lzr.LzrNumber;
import com.kingmang.lazurite.runtime.Value;

import javax.swing.*;


import static com.kingmang.lazurite.utils.ValueUtils.consumeFunction;

public class JMenuBarValue extends JComponentValue {

    final JMenuBar bar;

    public JMenuBarValue(JMenuBar bar) {
        super(1, bar);
        this.bar = bar;
        init();
    }

    private void init() {
        set("add", new LzrFunction(this::add));
    }

    private Value add(Value... args) {
        JMenu menu = new JMenu(args[0].asString());
        bar.add(menu);
        for(int i = 1; i < args.length; i++){
            menu.add(new JMenuItem(args[i].asString()));
        }

        return LzrNumber.ZERO;
    }
}