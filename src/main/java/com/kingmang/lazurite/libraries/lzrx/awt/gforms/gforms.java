package com.kingmang.lazurite.libraries.lzrx.awt.gforms;

import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.DarculaTheme;
import com.github.weisj.darklaf.theme.IntelliJTheme;
import com.kingmang.lazurite.libraries.Keyword;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.runtime.values.LzrMap;
import com.kingmang.lazurite.runtime.values.LzrNumber;
import com.kingmang.lazurite.runtime.values.LzrString;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

@SuppressWarnings({"unused", "ClassName"})
public final class gforms implements Library {

    public static void initConstant() {
        // JFrame constants
        Variables.define("DISPOSE_ON_CLOSE", LzrNumber.of(JFrame.DISPOSE_ON_CLOSE));
        Variables.define("DO_NOTHING_ON_CLOSE", LzrNumber.of(JFrame.DO_NOTHING_ON_CLOSE));
        Variables.define("EXIT_ON_CLOSE", LzrNumber.of(JFrame.EXIT_ON_CLOSE));
        Variables.define("HIDE_ON_CLOSE", LzrNumber.of(JFrame.HIDE_ON_CLOSE));

        // SwingConstants
        final LzrMap swing = new LzrMap(18);
        swing.set("BOTTOM", LzrNumber.of(SwingConstants.BOTTOM));
        swing.set("CENTER", LzrNumber.of(SwingConstants.CENTER));
        swing.set("EAST", LzrNumber.of(SwingConstants.EAST));
        swing.set("HORIZONTAL", LzrNumber.of(SwingConstants.HORIZONTAL));
        swing.set("LEADING", LzrNumber.of(SwingConstants.LEADING));
        swing.set("LEFT", LzrNumber.of(SwingConstants.LEFT));
        swing.set("NEXT", LzrNumber.of(SwingConstants.NEXT));
        swing.set("NORTH", LzrNumber.of(SwingConstants.NORTH));
        swing.set("NORTH_EAST", LzrNumber.of(SwingConstants.NORTH_EAST));
        swing.set("NORTH_WEST", LzrNumber.of(SwingConstants.NORTH_WEST));
        swing.set("PREVIOUS", LzrNumber.of(SwingConstants.PREVIOUS));
        swing.set("RIGHT", LzrNumber.of(SwingConstants.RIGHT));
        swing.set("SOUTH", LzrNumber.of(SwingConstants.SOUTH));
        swing.set("SOUTH_EAST", LzrNumber.of(SwingConstants.SOUTH_EAST));
        swing.set("SOUTH_WEST", LzrNumber.of(SwingConstants.SOUTH_WEST));
        swing.set("TOP", LzrNumber.of(SwingConstants.TOP));
        swing.set("TRAILING", LzrNumber.of(SwingConstants.TRAILING));
        swing.set("VERTICAL", LzrNumber.of(SwingConstants.VERTICAL));
        swing.set("WEST", LzrNumber.of(SwingConstants.WEST));
        Variables.define("SwingConstants", swing);

        // LayoutManagers constants
        final LzrMap border = new LzrMap(12);
        border.set("AFTER_LAST_LINE", new LzrString(BorderLayout.AFTER_LAST_LINE));
        border.set("AFTER_LINE_ENDS", new LzrString(BorderLayout.AFTER_LINE_ENDS));
        border.set("BEFORE_FIRST_LINE", new LzrString(BorderLayout.BEFORE_FIRST_LINE));
        border.set("BEFORE_LINE_BEGINS", new LzrString(BorderLayout.BEFORE_LINE_BEGINS));
        border.set("CENTER", new LzrString(BorderLayout.CENTER));
        border.set("EAST", new LzrString(BorderLayout.EAST));
        border.set("LINE_END", new LzrString(BorderLayout.LINE_END));
        border.set("LINE_START", new LzrString(BorderLayout.LINE_START));
        border.set("NORTH", new LzrString(BorderLayout.NORTH));
        border.set("PAGE_END", new LzrString(BorderLayout.PAGE_END));
        border.set("PAGE_START", new LzrString(BorderLayout.PAGE_START));
        border.set("SOUTH", new LzrString(BorderLayout.SOUTH));
        border.set("WEST", new LzrString(BorderLayout.WEST));
        Variables.define("BorderLayout", border);
        
        // ScrollPane constants
        final LzrMap scrollpane = new LzrMap(21);
        scrollpane.set("COLUMN_HEADER", new LzrString(ScrollPaneConstants.COLUMN_HEADER));
        scrollpane.set("HORIZONTAL_SCROLLBAR", new LzrString(ScrollPaneConstants.HORIZONTAL_SCROLLBAR));
        scrollpane.set("HORIZONTAL_SCROLLBAR_POLICY", new LzrString(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_POLICY));
        scrollpane.set("LOWER_LEADING_CORNER", new LzrString(ScrollPaneConstants.LOWER_LEADING_CORNER));
        scrollpane.set("LOWER_LEFT_CORNER", new LzrString(ScrollPaneConstants.LOWER_LEFT_CORNER));
        scrollpane.set("LOWER_RIGHT_CORNER", new LzrString(ScrollPaneConstants.LOWER_RIGHT_CORNER));
        scrollpane.set("LOWER_TRAILING_CORNER", new LzrString(ScrollPaneConstants.LOWER_TRAILING_CORNER));
        scrollpane.set("ROW_HEADER", new LzrString(ScrollPaneConstants.ROW_HEADER));
        scrollpane.set("UPPER_LEADING_CORNER", new LzrString(ScrollPaneConstants.UPPER_LEADING_CORNER));
        scrollpane.set("UPPER_LEFT_CORNER", new LzrString(ScrollPaneConstants.UPPER_LEFT_CORNER));
        scrollpane.set("UPPER_RIGHT_CORNER", new LzrString(ScrollPaneConstants.UPPER_RIGHT_CORNER));
        scrollpane.set("UPPER_TRAILING_CORNER", new LzrString(ScrollPaneConstants.UPPER_TRAILING_CORNER));
        scrollpane.set("VERTICAL_SCROLLBAR", new LzrString(ScrollPaneConstants.VERTICAL_SCROLLBAR));
        scrollpane.set("VERTICAL_SCROLLBAR_POLICY", new LzrString(ScrollPaneConstants.VERTICAL_SCROLLBAR_POLICY));
        scrollpane.set("VIEWPORT", new LzrString(ScrollPaneConstants.VIEWPORT));
        scrollpane.set("HORIZONTAL_SCROLLBAR_ALWAYS", LzrNumber.of(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS));
        scrollpane.set("HORIZONTAL_SCROLLBAR_AS_NEEDED", LzrNumber.of(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED));
        scrollpane.set("HORIZONTAL_SCROLLBAR_NEVER", LzrNumber.of(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
        scrollpane.set("VERTICAL_SCROLLBAR_ALWAYS", LzrNumber.of(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS));
        scrollpane.set("VERTICAL_SCROLLBAR_AS_NEEDED", LzrNumber.of(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED));
        scrollpane.set("VERTICAL_SCROLLBAR_NEVER", LzrNumber.of(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER));
        Variables.define("ScrollPaneConstants", scrollpane);

        final LzrMap box = new LzrMap(4);
        box.set("LINE_AXIS", LzrNumber.of(BoxLayout.LINE_AXIS));
        box.set("PAGE_AXIS", LzrNumber.of(BoxLayout.PAGE_AXIS));
        box.set("X_AXIS", LzrNumber.of(BoxLayout.X_AXIS));
        box.set("Y_AXIS", LzrNumber.of(BoxLayout.Y_AXIS));
        Variables.define("BoxLayout", box);
        
        final LzrMap windowEvent = new LzrMap(11);
        windowEvent.set("WINDOW_FIRST", LzrNumber.of(WindowEvent.WINDOW_FIRST));
        windowEvent.set("WINDOW_OPENED", LzrNumber.of(WindowEvent.WINDOW_OPENED));
        windowEvent.set("WINDOW_CLOSING", LzrNumber.of(WindowEvent.WINDOW_CLOSING));
        windowEvent.set("WINDOW_CLOSED", LzrNumber.of(WindowEvent.WINDOW_CLOSED));
        windowEvent.set("WINDOW_ICONIFIED", LzrNumber.of(WindowEvent.WINDOW_ICONIFIED));
        windowEvent.set("WINDOW_DEICONIFIED", LzrNumber.of(WindowEvent.WINDOW_DEICONIFIED));
        windowEvent.set("WINDOW_ACTIVATED", LzrNumber.of(WindowEvent.WINDOW_ACTIVATED));
        windowEvent.set("WINDOW_DEACTIVATED", LzrNumber.of(WindowEvent.WINDOW_DEACTIVATED));
        windowEvent.set("WINDOW_GAINED_FOCUS", LzrNumber.of(WindowEvent.WINDOW_GAINED_FOCUS));
        windowEvent.set("WINDOW_LOST_FOCUS", LzrNumber.of(WindowEvent.WINDOW_LOST_FOCUS));
        windowEvent.set("WINDOW_STATE_CHANGED", LzrNumber.of(WindowEvent.WINDOW_STATE_CHANGED));
        windowEvent.set("WINDOW_LAST", LzrNumber.of(WindowEvent.WINDOW_LAST));
        Variables.define("FrameEvent", windowEvent);
    }

    @Override
    public void init() {

        initConstant();

        Keyword.put("Button", Components::newButton);
        Keyword.put("MenuBar", Components::newMenuBar);
        Keyword.put("Label", Components::newLabel);
        Keyword.put("Panel", Components::newPanel);
        Keyword.put("ProgressBar", Components::newProgressBar);
        Keyword.put("ScrollPane", Components::newScrollPane);
        Keyword.put("TextArea", Components::newTextArea);
        Keyword.put("TextField", Components::newTextField);
        Keyword.put("Frame", Components::newWindow);
        Keyword.put("FileChooser", Components::newFileChooser);


        // LayoutManagers
        Keyword.put("borderLayout", LayoutManagers::borderLayout);
        Keyword.put("boxLayout", LayoutManagers::boxLayout);
        Keyword.put("cardLayout", LayoutManagers::cardLayout);
        Keyword.put("gridLayout", LayoutManagers::gridLayout);
        Keyword.put("flowLayout", LayoutManagers::flowLayout);

    }
}
