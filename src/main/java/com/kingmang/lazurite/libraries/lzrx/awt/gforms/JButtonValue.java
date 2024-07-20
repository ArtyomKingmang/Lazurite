package com.kingmang.lazurite.libraries.lzrx.awt.gforms;

import javax.swing.*;

public class JButtonValue extends AbstractButtonValue {

    final JButton jButton;

    public JButtonValue(JButton jButton) {
        super(0, jButton);
        this.jButton = jButton;
        init();
    }

    private void init() {
    }
}