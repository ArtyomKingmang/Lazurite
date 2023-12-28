package com.kingmang.lazurite.libraries.gforms;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.runtime.Value;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Functions for working with components.
 */
public final class Components {

    private Components() { }

    static Value newWindow(Value[] args) {
        Arguments.checkOrOr(0, 1, args.length);
        String title = (args.length == 1) ? args[0].asString() : "";
        final JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return new JFrameValue(frame);
    }


    static Value newPanel(Value[] args) {
        Arguments.checkOrOr(0, 1, args.length);
        final JPanel panel = new JPanel();
        if (args.length == 1) {
            panel.setLayout( ((LayoutManagerValue) args[0]).layout );
        }
        return new JPanelValue(panel);
    }

    static Value newButton(Value[] args) {
        Arguments.checkOrOr(0, 1, args.length);
        String text = (args.length == 1) ? args[0].asString() : "";
        return new JButtonValue(new JButton(text));
    }

    static Value newLabel(Value[] args) {
        Arguments.checkRange(0, 2, args.length);
        String text = (args.length >= 1) ? args[0].asString() : "";
        int align = (args.length == 2) ? args[1].asInt() : SwingConstants.LEADING;
        return new JLabelValue(new JLabel(text, align));
    }

    static Value newTextField(Value[] args) {
        Arguments.checkRange(0, 2, args.length);
        String text = "";
        int cols = 0;
        switch (args.length) {
            case 1: {
                text = args[0].asString();
            } break;
            case 2: {
                text = args[0].asString();
                cols = args[1].asInt();
            } break;
        }
        return new JTextFieldValue(new JTextField(text, cols));
    }
    
    static Value newTextArea(Value[] args) {
        Arguments.checkRange(0, 3, args.length);
        String text = "";
        int rows = 0;
        int cols = 0;
        switch (args.length) {
            case 1: {
                text = args[0].asString();
            } break;
            case 2: {
                rows = args[0].asInt();
                cols = args[1].asInt();
            } break;
            case 3: {
                text = args[0].asString();
                rows = args[1].asInt();
                cols = args[2].asInt();
            } break;
        }
        return new JTextAreaValue(new JTextArea(text, rows, cols));
    }
    
    static Value newProgressBar(Value[] args) {
        Arguments.checkRange(0, 3, args.length);
        boolean isVertical = false;
        int min = 0;
        int max = 100;
        switch (args.length) {
            case 1: {
                isVertical = args[0].asInt() != 0;
            } break;
            case 2: {
                min = args[0].asInt();
                max = args[1].asInt();
            } break;
            case 3: {
                isVertical = args[0].asInt() != 0;
                min = args[1].asInt();
                max = args[2].asInt();
            } break;
        }
        return new JProgressBarValue(new JProgressBar(
                isVertical ? SwingConstants.VERTICAL : SwingConstants.HORIZONTAL,
                min, max
        ));
    }
    
    static Value newScrollPane(Value[] args) {
        Arguments.checkRange(0, 3, args.length);
        Component view = null;
        int vsbPolicy = JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED;
        int hsbPolicy = JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED;
        switch (args.length) {
            case 1: {
                view = ((ComponentValue) args[0]).component;
            } break;
            case 2: {
                vsbPolicy = args[0].asInt();
                hsbPolicy = args[1].asInt();
            } break;
            case 3: {
                view = ((ComponentValue) args[0]).component;
                vsbPolicy = args[1].asInt();
                hsbPolicy = args[2].asInt();
            } break;
        }
        return new JScrollPaneValue(new JScrollPane(
                view, vsbPolicy, hsbPolicy
        ));
    }
}
