package com.kingmang.lazurite.libraries.GForms;

import com.kingmang.lazurite.base.*;

import com.kingmang.lazurite.libraries.libraries;
import com.kingmang.lazurite.runtime.MapValue;
import com.kingmang.lazurite.runtime.NumberValue;
import com.kingmang.lazurite.runtime.StringValue;
import com.kingmang.lazurite.runtime.Variables;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import javax.swing.*;

import javax.swing.JFrame;




public final class GForms implements libraries {

    public static void initConstants() {
        // JFrame constants
        Variables.define("DISPOSE_ON_CLOSE", NumberValue.of(JFrame.DISPOSE_ON_CLOSE));
        Variables.define("DO_NOTHING_ON_CLOSE", NumberValue.of(JFrame.DO_NOTHING_ON_CLOSE));
        Variables.define("EXIT_ON_CLOSE", NumberValue.of(JFrame.EXIT_ON_CLOSE));
        Variables.define("HIDE_ON_CLOSE", NumberValue.of(JFrame.HIDE_ON_CLOSE));

        // SwinfConstants
        final MapValue swing = new MapValue(20);
        swing.set("BOTTOM", NumberValue.of(SwingConstants.BOTTOM));
        swing.set("CENTER", NumberValue.of(SwingConstants.CENTER));
        swing.set("EAST", NumberValue.of(SwingConstants.EAST));
        swing.set("HORIZONTAL", NumberValue.of(SwingConstants.HORIZONTAL));
        swing.set("LEADING", NumberValue.of(SwingConstants.LEADING));
        swing.set("LEFT", NumberValue.of(SwingConstants.LEFT));
        swing.set("NEXT", NumberValue.of(SwingConstants.NEXT));
        swing.set("NORTH", NumberValue.of(SwingConstants.NORTH));
        swing.set("NORTH_EAST", NumberValue.of(SwingConstants.NORTH_EAST));
        swing.set("NORTH_WEST", NumberValue.of(SwingConstants.NORTH_WEST));
        swing.set("PREVIOUS", NumberValue.of(SwingConstants.PREVIOUS));
        swing.set("RIGHT", NumberValue.of(SwingConstants.RIGHT));
        swing.set("SOUTH", NumberValue.of(SwingConstants.SOUTH));
        swing.set("SOUTH_EAST", NumberValue.of(SwingConstants.SOUTH_EAST));
        swing.set("SOUTH_WEST", NumberValue.of(SwingConstants.SOUTH_WEST));
        swing.set("TOP", NumberValue.of(SwingConstants.TOP));
        swing.set("TRAILING", NumberValue.of(SwingConstants.TRAILING));
        swing.set("VERTICAL", NumberValue.of(SwingConstants.VERTICAL));
        swing.set("WEST", NumberValue.of(SwingConstants.WEST));
        Variables.define("SwingConstants", swing);

        // LayoutManagers constants
        final MapValue border = new MapValue(13);
        border.set("AFTER_LAST_LINE", new StringValue(BorderLayout.AFTER_LAST_LINE));
        border.set("AFTER_LINE_ENDS", new StringValue(BorderLayout.AFTER_LINE_ENDS));
        border.set("BEFORE_FIRST_LINE", new StringValue(BorderLayout.BEFORE_FIRST_LINE));
        border.set("BEFORE_LINE_BEGINS", new StringValue(BorderLayout.BEFORE_LINE_BEGINS));
        border.set("CENTER", new StringValue(BorderLayout.CENTER));
        border.set("EAST", new StringValue(BorderLayout.EAST));
        border.set("LINE_END", new StringValue(BorderLayout.LINE_END));
        border.set("LINE_START", new StringValue(BorderLayout.LINE_START));
        border.set("NORTH", new StringValue(BorderLayout.NORTH));
        border.set("PAGE_END", new StringValue(BorderLayout.PAGE_END));
        border.set("PAGE_START", new StringValue(BorderLayout.PAGE_START));
        border.set("SOUTH", new StringValue(BorderLayout.SOUTH));
        border.set("WEST", new StringValue(BorderLayout.WEST));
        Variables.define("BorderLayout", border);
        
        // ScrollPane constants
        final MapValue scrollpane = new MapValue(13);
        scrollpane.set("COLUMN_HEADER", new StringValue(ScrollPaneConstants.COLUMN_HEADER));
        scrollpane.set("HORIZONTAL_SCROLLBAR", new StringValue(ScrollPaneConstants.HORIZONTAL_SCROLLBAR));
        scrollpane.set("HORIZONTAL_SCROLLBAR_POLICY", new StringValue(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_POLICY));
        scrollpane.set("LOWER_LEADING_CORNER", new StringValue(ScrollPaneConstants.LOWER_LEADING_CORNER));
        scrollpane.set("LOWER_LEFT_CORNER", new StringValue(ScrollPaneConstants.LOWER_LEFT_CORNER));
        scrollpane.set("LOWER_RIGHT_CORNER", new StringValue(ScrollPaneConstants.LOWER_RIGHT_CORNER));
        scrollpane.set("LOWER_TRAILING_CORNER", new StringValue(ScrollPaneConstants.LOWER_TRAILING_CORNER));
        scrollpane.set("ROW_HEADER", new StringValue(ScrollPaneConstants.ROW_HEADER));
        scrollpane.set("UPPER_LEADING_CORNER", new StringValue(ScrollPaneConstants.UPPER_LEADING_CORNER));
        scrollpane.set("UPPER_LEFT_CORNER", new StringValue(ScrollPaneConstants.UPPER_LEFT_CORNER));
        scrollpane.set("UPPER_RIGHT_CORNER", new StringValue(ScrollPaneConstants.UPPER_RIGHT_CORNER));
        scrollpane.set("UPPER_TRAILING_CORNER", new StringValue(ScrollPaneConstants.UPPER_TRAILING_CORNER));
        scrollpane.set("VERTICAL_SCROLLBAR", new StringValue(ScrollPaneConstants.VERTICAL_SCROLLBAR));
        scrollpane.set("VERTICAL_SCROLLBAR_POLICY", new StringValue(ScrollPaneConstants.VERTICAL_SCROLLBAR_POLICY));
        scrollpane.set("VIEWPORT", new StringValue(ScrollPaneConstants.VIEWPORT));
        scrollpane.set("HORIZONTAL_SCROLLBAR_ALWAYS", NumberValue.of(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS));
        scrollpane.set("HORIZONTAL_SCROLLBAR_AS_NEEDED", NumberValue.of(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED));
        scrollpane.set("HORIZONTAL_SCROLLBAR_NEVER", NumberValue.of(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
        scrollpane.set("VERTICAL_SCROLLBAR_ALWAYS", NumberValue.of(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS));
        scrollpane.set("VERTICAL_SCROLLBAR_AS_NEEDED", NumberValue.of(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED));
        scrollpane.set("VERTICAL_SCROLLBAR_NEVER", NumberValue.of(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER));
        Variables.define("ScrollPaneConstants", scrollpane);

        final MapValue box = new MapValue(4);
        box.set("LINE_AXIS", NumberValue.of(BoxLayout.LINE_AXIS));
        box.set("PAGE_AXIS", NumberValue.of(BoxLayout.PAGE_AXIS));
        box.set("X_AXIS", NumberValue.of(BoxLayout.X_AXIS));
        box.set("Y_AXIS", NumberValue.of(BoxLayout.Y_AXIS));
        Variables.define("BoxLayout", box);
        
        final MapValue windowEvent = new MapValue(4);
        windowEvent.set("WINDOW_FIRST", NumberValue.of(WindowEvent.WINDOW_FIRST));
        windowEvent.set("WINDOW_OPENED", NumberValue.of(WindowEvent.WINDOW_OPENED));
        windowEvent.set("WINDOW_CLOSING", NumberValue.of(WindowEvent.WINDOW_CLOSING));
        windowEvent.set("WINDOW_CLOSED", NumberValue.of(WindowEvent.WINDOW_CLOSED));
        windowEvent.set("WINDOW_ICONIFIED", NumberValue.of(WindowEvent.WINDOW_ICONIFIED));
        windowEvent.set("WINDOW_DEICONIFIED", NumberValue.of(WindowEvent.WINDOW_DEICONIFIED));
        windowEvent.set("WINDOW_ACTIVATED", NumberValue.of(WindowEvent.WINDOW_ACTIVATED));
        windowEvent.set("WINDOW_DEACTIVATED", NumberValue.of(WindowEvent.WINDOW_DEACTIVATED));
        windowEvent.set("WINDOW_GAINED_FOCUS", NumberValue.of(WindowEvent.WINDOW_GAINED_FOCUS));
        windowEvent.set("WINDOW_LOST_FOCUS", NumberValue.of(WindowEvent.WINDOW_LOST_FOCUS));
        windowEvent.set("WINDOW_STATE_CHANGED", NumberValue.of(WindowEvent.WINDOW_STATE_CHANGED));
        windowEvent.set("WINDOW_LAST", NumberValue.of(WindowEvent.WINDOW_LAST));
        Variables.define("WindowEvent", windowEvent);
    }

    @Override
    public void init() {
        initConstants();
        // Components
        KEYWORD.put("Button", Components::newButton);
        KEYWORD.put("Label", Components::newLabel);
        KEYWORD.put("Panel", Components::newPanel);
        KEYWORD.put("ProgressBar", Components::newProgressBar);
        KEYWORD.put("ScrollPane", Components::newScrollPane);
        KEYWORD.put("TextArea", Components::newTextArea);
        KEYWORD.put("TextField", Components::newTextField);
        KEYWORD.put("Frame", Components::newWindow);


        // LayoutManagers
        KEYWORD.put("borderLayout", LayoutManagers::borderLayout);
        KEYWORD.put("boxLayout", LayoutManagers::boxLayout);
        KEYWORD.put("cardLayout", LayoutManagers::cardLayout);
        KEYWORD.put("gridLayout", LayoutManagers::gridLayout);
        KEYWORD.put("flowLayout", LayoutManagers::flowLayout);

    }
}
