package com.kingmang.lazurite.libraries.lzrx.awt.gforms;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.runtime.values.LzrFunction;
import com.kingmang.lazurite.runtime.values.LzrNumber;
import com.kingmang.lazurite.runtime.values.LzrValue;

import javax.swing.*;
import java.awt.event.ActionListener;

public class JMenuBarValue extends JComponentValue {
    JMenuItem[] items;
    final JMenuBar bar;
    public JMenuBarValue(JMenuBar bar) {
        super(1, bar);
        this.bar = bar;
        init();
    }

    private void init() {
        set("add", new LzrFunction(this::add));
        set("setAction", new LzrFunction(this::setAction));
    }

    private LzrValue add(LzrValue... args) {
        JMenu menu = new JMenu(args[0].asString());
        bar.add(menu);
        items = new JMenuItem[10];

        for(int i = 1; i < args.length; i++) {
            items[i] = new JMenuItem(args[i].asString());
            menu.add(items[i]);
        }

        return LzrNumber.ZERO;
    }

    private LzrValue setAction(LzrValue... args) {
        Function body;
        body = ((LzrFunction) args[1]).getValue();
        ActionListener enableActionListener = actionEvent -> body.execute();
        items[args[0].asInt()].addActionListener(enableActionListener);


        return LzrNumber.ZERO;
    }
}

