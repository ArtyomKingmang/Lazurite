package com.kingmang.lazurite.libraries.lzrx.awt.gforms;

import javax.swing.*;

public class JPanelValue extends JComponentValue {

    final JPanel panel;

    public JPanelValue(JPanel panel) {
        super(0, panel);
        this.panel = panel;
        init();
    }

    private void init() {
    }
}