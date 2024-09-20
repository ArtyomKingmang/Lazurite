package com.kingmang.lazurite.libraries.lzrx.awt.gforms;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.DarculaTheme;
import com.github.weisj.darklaf.theme.IntelliJTheme;
import com.github.weisj.darklaf.theme.OneDarkTheme;
import com.github.weisj.darklaf.theme.SolarizedDarkTheme;
import com.github.weisj.darklaf.theme.laf.IntelliJThemeDarklafLookAndFeel;
import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.runtime.values.LzrNumber;
import com.kingmang.lazurite.runtime.values.LzrValue;
import lombok.NoArgsConstructor;

import javax.swing.*;
import java.awt.*;

@NoArgsConstructor
public final class Components {

    static LzrValue newWindow(LzrValue[] args) {
        Arguments.checkOrOr(0, 1, args.length);
        String title = (args.length == 1) ? args[0].asString() : "";
        final JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        return new JFrameValue(frame);
    }

    static LzrValue newPanel(LzrValue[] args) {
        Arguments.checkOrOr(0, 1, args.length);
        final JPanel panel = new JPanel();
        if (args.length == 1) {
            panel.setLayout( ((LayoutManagerValue) args[0]).layout );
        }
        return new JPanelValue(panel);
    }

    static LzrValue newButton(LzrValue[] args) {
        Arguments.checkOrOr(0, 1, args.length);
        String text = (args.length == 1) ? args[0].asString() : "";
        return new JButtonValue(new JButton(text));
    }

    static LzrValue newMenuBar(LzrValue[] args) {
        return new JMenuBarValue(new JMenuBar());
    }

    static LzrValue newLabel(LzrValue[] args) {
        Arguments.checkRange(0, 2, args.length);
        String text = (args.length >= 1) ? args[0].asString() : "";
        int align = (args.length == 2) ? args[1].asInt() : SwingConstants.LEADING;
        return new JLabelValue(new JLabel(text, align));
    }

    static LzrValue newFileChooser(LzrValue[] args) {
        return new JFileChooserValue(new JFileChooser());
    }

    static LzrValue newTextField(LzrValue[] args) {
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
    
    static LzrValue newTextArea(LzrValue[] args) {
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
    
    static LzrValue newProgressBar(LzrValue[] args) {
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
    
    static LzrValue newScrollPane(LzrValue[] args) {
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
