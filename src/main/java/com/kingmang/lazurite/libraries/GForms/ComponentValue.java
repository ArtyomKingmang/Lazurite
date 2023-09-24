package com.kingmang.lazurite.libraries.GForms;


import com.kingmang.lazurite.core.*;
import com.kingmang.lazurite.runtime.*;
import com.kingmang.lazurite.runtime.LZR.*;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.function.Consumer;
import java.util.function.Supplier;
import static com.kingmang.lazurite.core.Converters.booleanOptToVoid;

public abstract class ComponentValue extends LZRMap {

    final Component component;

    public ComponentValue(int functionsCount, Component component) {
        super(functionsCount + 42);
        this.component = component;
        init();
    }

    private void init() {
        set("onKeyAction", new LZRFunction(this::addKeyListener));
        set("addKeyListener", new LZRFunction(this::addKeyListener));
        set("getFocusTraversalKeysEnabled", Converters.voidToBoolean(component::getFocusTraversalKeysEnabled));
        set("getHeight", Converters.voidToInt(component::getHeight));
        set("getIgnoreRepaint", Converters.voidToBoolean(component::getIgnoreRepaint));
        set("getLocation", new LZRFunction(this::getLocation));
        set("getLocationOnScreen", new LZRFunction(this::getLocationOnScreen));
        set("getMinimumSize", dimensionFunction(component::getMinimumSize));
        set("getMaximumSize", dimensionFunction(component::getMaximumSize));
        set("getName", Converters.voidToString(component::getName));
        set("getPreferredSize", dimensionFunction(component::getPreferredSize));
        set("getSize", dimensionFunction(component::getSize));
        set("getWidth", Converters.voidToInt(component::getWidth));
        set("getX", Converters.voidToInt(component::getX));
        set("getY", Converters.voidToInt(component::getY));
        set("hasFocus", Converters.voidToBoolean(component::hasFocus));
        set("invalidate", Converters.voidToVoid(component::invalidate));

        set("isDisplayable", Converters.voidToBoolean(component::isDisplayable));
        set("isDoubleBuffered", Converters.voidToBoolean(component::isDoubleBuffered));
        set("isEnabled", Converters.voidToBoolean(component::isEnabled));
        set("isFocusOwner", Converters.voidToBoolean(component::isFocusOwner));
        set("isFocusable", Converters.voidToBoolean(component::isFocusable));
        set("isLightweight", Converters.voidToBoolean(component::isLightweight));
        set("isOpaque", Converters.voidToBoolean(component::isOpaque));
        set("isShowing", Converters.voidToBoolean(component::isShowing));
        set("isValid", Converters.voidToBoolean(component::isValid));
        set("isVisible", Converters.voidToBoolean(component::isVisible));

        set("requestFocus", Converters.voidToVoid(component::requestFocus));
        set("requestFocusInWindow", Converters.voidToBoolean(component::requestFocusInWindow));
        set("repaint", Converters.voidToVoid(component::repaint));
        set("revalidate", Converters.voidToVoid(component::revalidate));
        set("setMaximumSize", voidDimensionFunction(component::setMaximumSize));
        set("setMinimumSize", voidDimensionFunction(component::setMinimumSize));
        set("setName", Converters.stringToVoid(component::setName));
        set("setPreferredSize", voidDimensionFunction(component::setPreferredSize));
        set("setSize", voidDimensionFunction(component::setSize));
        set("setVisible", Converters.booleanOptToVoid(component::setVisible));
        set("setLocation", new LZRFunction(this::setLocation));
        set("validate", Converters.voidToVoid(component::validate));
    }

    private Value addKeyListener(Value[] args) {
        Arguments.check(1, args.length);
        final Function action = ValueUtils.consumeFunction(args[0], 0);
        component.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                handleKeyEvent("typed", e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyEvent("pressed", e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                handleKeyEvent("released", e);
            }

            private void handleKeyEvent(String type, final KeyEvent e) {
                final LZRMap map = new LZRMap(15);
                map.set("extendedKeyCode", LZRNumber.of(e.getExtendedKeyCode()));
                map.set("keyChar", LZRNumber.of(e.getKeyChar()));
                map.set("keyCode", LZRNumber.of(e.getKeyCode()));
                map.set("keyLocation", LZRNumber.of(e.getKeyLocation()));
                map.set("id", LZRNumber.of(e.getID()));
                map.set("isActionKey", LZRNumber.fromBoolean(e.isActionKey()));
                map.set("isAltDown", LZRNumber.fromBoolean(e.isAltDown()));
                map.set("isAltGraphDown", LZRNumber.fromBoolean(e.isAltGraphDown()));
                map.set("isConsumed", LZRNumber.fromBoolean(e.isConsumed()));
                map.set("isControlDown", LZRNumber.fromBoolean(e.isControlDown()));
                map.set("isMetaDown", LZRNumber.fromBoolean(e.isMetaDown()));
                map.set("isShiftDown", LZRNumber.fromBoolean(e.isShiftDown()));
                map.set("modifiers", LZRNumber.of(e.getModifiers()));
                action.execute(new LZRString(type), map);
            }
        });
        return LZRNumber.ZERO;
    }

    private Value getLocation(Value[] args) {
        final Point location = component.getLocation();
        final LZRArray result = new LZRArray(2);
        result.set(0, LZRNumber.of(location.x));
        result.set(1, LZRNumber.of(location.y));
        return result;
    }

    private Value getLocationOnScreen(Value[] args) {
        final Point location = component.getLocationOnScreen();
        final LZRArray result = new LZRArray(2);
        result.set(0, LZRNumber.of(location.x));
        result.set(1, LZRNumber.of(location.y));
        return result;
    }

    private Value setLocation(Value[] args) {
        Arguments.check(2, args.length);
        component.setLocation(args[0].asInt(), args[1].asInt());
        return LZRNumber.ZERO;
    }



    protected static LZRFunction dimensionFunction(Supplier<Dimension> s) {
        return new LZRFunction(args -> {
            final Dimension dimension = s.get();
            final LZRArray result = new LZRArray(2);
            result.set(0, LZRNumber.of(dimension.getWidth()));
            result.set(1, LZRNumber.of(dimension.getHeight()));
            return result;
        });
    }

    protected static LZRFunction voidDimensionFunction(Consumer<Dimension> s) {
        return new LZRFunction(args -> {
            Arguments.check(2, args.length);
            s.accept(new Dimension(args[0].asInt(), args[1].asInt()));
            return LZRNumber.ZERO;
        });
    }
}