package com.kingmang.lazurite.libraries.lzrx.awt.gforms;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.runtime.values.LzrValue;

import javax.swing.*;
import java.awt.*;

/**
 * Functions for working with layout managers.
 */
public final class LayoutManagers {

    private LayoutManagers() { }

    static LzrValue borderLayout(LzrValue[] args) {
        Arguments.checkOrOr(0, 2, args.length);
        int hgap = (args.length == 2) ? args[0].asInt() : 0;
        int vgap = (args.length == 2) ? args[1].asInt() : 0;
        return new LayoutManagerValue(
                new BorderLayout(hgap, vgap)
        );
    }

    static LzrValue boxLayout(LzrValue[] args) {
        Arguments.checkOrOr(1, 2, args.length);
        int axis = (args.length == 2) ? args[1].asInt() : BoxLayout.PAGE_AXIS;
        return new LayoutManagerValue(
                new BoxLayout(((JPanelValue) args[0]).panel, axis)
        );
    }

    static LzrValue cardLayout(LzrValue[] args) {
        Arguments.checkOrOr(0, 2, args.length);
        int hgap = (args.length == 2) ? args[0].asInt() : 0;
        int vgap = (args.length == 2) ? args[1].asInt() : 0;
        return new LayoutManagerValue(
                new CardLayout(hgap, vgap)
        );
    }

    static LzrValue gridLayout(LzrValue[] args) {
        Arguments.checkRange(0, 4, args.length);
        int rows = 1, cols = 0, hgap = 0, vgap = 0;
        switch (args.length) {
            case 1:
                rows = args[0].asInt();
                break;
            case 2:
                rows = args[0].asInt();
                cols = args[1].asInt();
                break;
            case 3:
                rows = args[0].asInt();
                cols = args[1].asInt();
                hgap = args[2].asInt();
                break;
            case 4:
                rows = args[0].asInt();
                cols = args[1].asInt();
                hgap = args[2].asInt();
                vgap = args[3].asInt();
                break;
        }
        return new LayoutManagerValue(
                new GridLayout(rows, cols, hgap, vgap)
        );
    }

    static LzrValue flowLayout(LzrValue[] args) {
        Arguments.checkRange(0, 3, args.length);
        final int align, hgap, vgap;
        switch (args.length) {
            case 1:
                align = args[0].asInt();
                hgap = 5;
                vgap = 5;
                break;
            case 2:
                align = FlowLayout.CENTER;
                hgap = args[0].asInt();
                vgap = args[1].asInt();
                break;
            case 3:
                align = args[0].asInt();
                hgap = args[1].asInt();
                vgap = args[2].asInt();
                break;
            default:
                align = FlowLayout.CENTER;
                hgap = 5;
                vgap = 5;
                break;
        }

        return new LayoutManagerValue(
                new FlowLayout(align, hgap, vgap)
        );
    }
}
