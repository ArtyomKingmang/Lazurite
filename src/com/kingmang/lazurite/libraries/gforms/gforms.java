package com.kingmang.lazurite.libraries.gforms;

import com.kingmang.lazurite.libraries.Keyword;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.LZR.LZRMap;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
import com.kingmang.lazurite.runtime.LZR.LZRString;
import com.kingmang.lazurite.runtime.Variables;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import javax.swing.*;

import javax.swing.JFrame;

public final class gforms implements Library {

    public static void initConstant() {
        // JFrame constants
        Variables.define("DISPOSE_ON_CLOSE", LZRNumber.of(JFrame.DISPOSE_ON_CLOSE));
        Variables.define("DO_NOTHING_ON_CLOSE", LZRNumber.of(JFrame.DO_NOTHING_ON_CLOSE));
        Variables.define("EXIT_ON_CLOSE", LZRNumber.of(JFrame.EXIT_ON_CLOSE));
        Variables.define("HIDE_ON_CLOSE", LZRNumber.of(JFrame.HIDE_ON_CLOSE));

        // SwinfConstants
        final LZRMap swing = new LZRMap(20);
        swing.set("BOTTOM", LZRNumber.of(SwingConstants.BOTTOM));
        swing.set("CENTER", LZRNumber.of(SwingConstants.CENTER));
        swing.set("EAST", LZRNumber.of(SwingConstants.EAST));
        swing.set("HORIZONTAL", LZRNumber.of(SwingConstants.HORIZONTAL));
        swing.set("LEADING", LZRNumber.of(SwingConstants.LEADING));
        swing.set("LEFT", LZRNumber.of(SwingConstants.LEFT));
        swing.set("NEXT", LZRNumber.of(SwingConstants.NEXT));
        swing.set("NORTH", LZRNumber.of(SwingConstants.NORTH));
        swing.set("NORTH_EAST", LZRNumber.of(SwingConstants.NORTH_EAST));
        swing.set("NORTH_WEST", LZRNumber.of(SwingConstants.NORTH_WEST));
        swing.set("PREVIOUS", LZRNumber.of(SwingConstants.PREVIOUS));
        swing.set("RIGHT", LZRNumber.of(SwingConstants.RIGHT));
        swing.set("SOUTH", LZRNumber.of(SwingConstants.SOUTH));
        swing.set("SOUTH_EAST", LZRNumber.of(SwingConstants.SOUTH_EAST));
        swing.set("SOUTH_WEST", LZRNumber.of(SwingConstants.SOUTH_WEST));
        swing.set("TOP", LZRNumber.of(SwingConstants.TOP));
        swing.set("TRAILING", LZRNumber.of(SwingConstants.TRAILING));
        swing.set("VERTICAL", LZRNumber.of(SwingConstants.VERTICAL));
        swing.set("WEST", LZRNumber.of(SwingConstants.WEST));
        Variables.define("SwingConstants", swing);

        // LayoutManagers constants
        final LZRMap border = new LZRMap(13);
        border.set("AFTER_LAST_LINE", new LZRString(BorderLayout.AFTER_LAST_LINE));
        border.set("AFTER_LINE_ENDS", new LZRString(BorderLayout.AFTER_LINE_ENDS));
        border.set("BEFORE_FIRST_LINE", new LZRString(BorderLayout.BEFORE_FIRST_LINE));
        border.set("BEFORE_LINE_BEGINS", new LZRString(BorderLayout.BEFORE_LINE_BEGINS));
        border.set("CENTER", new LZRString(BorderLayout.CENTER));
        border.set("EAST", new LZRString(BorderLayout.EAST));
        border.set("LINE_END", new LZRString(BorderLayout.LINE_END));
        border.set("LINE_START", new LZRString(BorderLayout.LINE_START));
        border.set("NORTH", new LZRString(BorderLayout.NORTH));
        border.set("PAGE_END", new LZRString(BorderLayout.PAGE_END));
        border.set("PAGE_START", new LZRString(BorderLayout.PAGE_START));
        border.set("SOUTH", new LZRString(BorderLayout.SOUTH));
        border.set("WEST", new LZRString(BorderLayout.WEST));
        Variables.define("BorderLayout", border);
        
        // ScrollPane constants
        final LZRMap scrollpane = new LZRMap(13);
        scrollpane.set("COLUMN_HEADER", new LZRString(ScrollPaneConstants.COLUMN_HEADER));
        scrollpane.set("HORIZONTAL_SCROLLBAR", new LZRString(ScrollPaneConstants.HORIZONTAL_SCROLLBAR));
        scrollpane.set("HORIZONTAL_SCROLLBAR_POLICY", new LZRString(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_POLICY));
        scrollpane.set("LOWER_LEADING_CORNER", new LZRString(ScrollPaneConstants.LOWER_LEADING_CORNER));
        scrollpane.set("LOWER_LEFT_CORNER", new LZRString(ScrollPaneConstants.LOWER_LEFT_CORNER));
        scrollpane.set("LOWER_RIGHT_CORNER", new LZRString(ScrollPaneConstants.LOWER_RIGHT_CORNER));
        scrollpane.set("LOWER_TRAILING_CORNER", new LZRString(ScrollPaneConstants.LOWER_TRAILING_CORNER));
        scrollpane.set("ROW_HEADER", new LZRString(ScrollPaneConstants.ROW_HEADER));
        scrollpane.set("UPPER_LEADING_CORNER", new LZRString(ScrollPaneConstants.UPPER_LEADING_CORNER));
        scrollpane.set("UPPER_LEFT_CORNER", new LZRString(ScrollPaneConstants.UPPER_LEFT_CORNER));
        scrollpane.set("UPPER_RIGHT_CORNER", new LZRString(ScrollPaneConstants.UPPER_RIGHT_CORNER));
        scrollpane.set("UPPER_TRAILING_CORNER", new LZRString(ScrollPaneConstants.UPPER_TRAILING_CORNER));
        scrollpane.set("VERTICAL_SCROLLBAR", new LZRString(ScrollPaneConstants.VERTICAL_SCROLLBAR));
        scrollpane.set("VERTICAL_SCROLLBAR_POLICY", new LZRString(ScrollPaneConstants.VERTICAL_SCROLLBAR_POLICY));
        scrollpane.set("VIEWPORT", new LZRString(ScrollPaneConstants.VIEWPORT));
        scrollpane.set("HORIZONTAL_SCROLLBAR_ALWAYS", LZRNumber.of(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS));
        scrollpane.set("HORIZONTAL_SCROLLBAR_AS_NEEDED", LZRNumber.of(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED));
        scrollpane.set("HORIZONTAL_SCROLLBAR_NEVER", LZRNumber.of(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
        scrollpane.set("VERTICAL_SCROLLBAR_ALWAYS", LZRNumber.of(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS));
        scrollpane.set("VERTICAL_SCROLLBAR_AS_NEEDED", LZRNumber.of(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED));
        scrollpane.set("VERTICAL_SCROLLBAR_NEVER", LZRNumber.of(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER));
        Variables.define("ScrollPaneConstants", scrollpane);

        final LZRMap box = new LZRMap(4);
        box.set("LINE_AXIS", LZRNumber.of(BoxLayout.LINE_AXIS));
        box.set("PAGE_AXIS", LZRNumber.of(BoxLayout.PAGE_AXIS));
        box.set("X_AXIS", LZRNumber.of(BoxLayout.X_AXIS));
        box.set("Y_AXIS", LZRNumber.of(BoxLayout.Y_AXIS));
        Variables.define("BoxLayout", box);
        
        final LZRMap windowEvent = new LZRMap(4);
        windowEvent.set("WINDOW_FIRST", LZRNumber.of(WindowEvent.WINDOW_FIRST));
        windowEvent.set("WINDOW_OPENED", LZRNumber.of(WindowEvent.WINDOW_OPENED));
        windowEvent.set("WINDOW_CLOSING", LZRNumber.of(WindowEvent.WINDOW_CLOSING));
        windowEvent.set("WINDOW_CLOSED", LZRNumber.of(WindowEvent.WINDOW_CLOSED));
        windowEvent.set("WINDOW_ICONIFIED", LZRNumber.of(WindowEvent.WINDOW_ICONIFIED));
        windowEvent.set("WINDOW_DEICONIFIED", LZRNumber.of(WindowEvent.WINDOW_DEICONIFIED));
        windowEvent.set("WINDOW_ACTIVATED", LZRNumber.of(WindowEvent.WINDOW_ACTIVATED));
        windowEvent.set("WINDOW_DEACTIVATED", LZRNumber.of(WindowEvent.WINDOW_DEACTIVATED));
        windowEvent.set("WINDOW_GAINED_FOCUS", LZRNumber.of(WindowEvent.WINDOW_GAINED_FOCUS));
        windowEvent.set("WINDOW_LOST_FOCUS", LZRNumber.of(WindowEvent.WINDOW_LOST_FOCUS));
        windowEvent.set("WINDOW_STATE_CHANGED", LZRNumber.of(WindowEvent.WINDOW_STATE_CHANGED));
        windowEvent.set("WINDOW_LAST", LZRNumber.of(WindowEvent.WINDOW_LAST));
        Variables.define("WindowEvent", windowEvent);
    }

    @Override
    public void init() {
        initConstant();
        // Components
        Keyword.put("Button", com.kingmang.lazurite.libraries.gforms.Components::newButton);
        Keyword.put("Label", com.kingmang.lazurite.libraries.gforms.Components::newLabel);
        Keyword.put("Panel", com.kingmang.lazurite.libraries.gforms.Components::newPanel);
        Keyword.put("ProgressBar", com.kingmang.lazurite.libraries.gforms.Components::newProgressBar);
        Keyword.put("ScrollPane", com.kingmang.lazurite.libraries.gforms.Components::newScrollPane);
        Keyword.put("TextArea", com.kingmang.lazurite.libraries.gforms.Components::newTextArea);
        Keyword.put("TextField", com.kingmang.lazurite.libraries.gforms.Components::newTextField);
        Keyword.put("Frame", Components::newWindow);


        // LayoutManagers
        Keyword.put("borderLayout", com.kingmang.lazurite.libraries.gforms.LayoutManagers::borderLayout);
        Keyword.put("boxLayout", com.kingmang.lazurite.libraries.gforms.LayoutManagers::boxLayout);
        Keyword.put("cardLayout", com.kingmang.lazurite.libraries.gforms.LayoutManagers::cardLayout);
        Keyword.put("gridLayout", com.kingmang.lazurite.libraries.gforms.LayoutManagers::gridLayout);
        Keyword.put("flowLayout", LayoutManagers::flowLayout);

    }
}
