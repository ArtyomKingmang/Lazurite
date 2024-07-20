package com.kingmang.lazurite.libraries.lzrx.awt.gforms;


import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Converters;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.runtime.values.*;
import com.kingmang.lazurite.utils.ValueUtils;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.kingmang.lazurite.core.Converters.booleanOptToVoid;

public abstract class ComponentValue extends LzrMap {

    final Component component;

    public ComponentValue(int functionsCount, Component component) {
        super(functionsCount + 42);
        this.component = component;
        init();
    }

    private void init() {
        set("setFont", new LzrFunction(this::setFont));
        set("getFont", new LzrFunction(this::getFont));
        set("onKeyAction", new LzrFunction(this::addKeyListener));
        set("addKeyListener", new LzrFunction(this::addKeyListener));
        set("getFocusTraversalKeysEnabled", Converters.voidToBoolean(component::getFocusTraversalKeysEnabled));
        set("getHeight", Converters.voidToInt(component::getHeight));
        set("getIgnoreRepaint", Converters.voidToBoolean(component::getIgnoreRepaint));
        set("getLocation", new LzrFunction(this::getLocation));
        set("getLocationOnScreen", new LzrFunction(this::getLocationOnScreen));
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
        set("setVisible", booleanOptToVoid(component::setVisible));
        set("setLocation", new LzrFunction(this::setLocation));
        set("validate", Converters.voidToVoid(component::validate));
    }

    private LzrValue addKeyListener(LzrValue[] args) {
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
                final LzrMap map = new LzrMap(15);
                map.set("extendedKeyCode", LzrNumber.of(e.getExtendedKeyCode()));
                map.set("keyChar", LzrNumber.of(e.getKeyChar()));
                map.set("keyCode", LzrNumber.of(e.getKeyCode()));
                map.set("keyLocation", LzrNumber.of(e.getKeyLocation()));
                map.set("id", LzrNumber.of(e.getID()));
                map.set("isActionKey", LzrNumber.fromBoolean(e.isActionKey()));
                map.set("isAltDown", LzrNumber.fromBoolean(e.isAltDown()));
                map.set("isAltGraphDown", LzrNumber.fromBoolean(e.isAltGraphDown()));
                map.set("isConsumed", LzrNumber.fromBoolean(e.isConsumed()));
                map.set("isControlDown", LzrNumber.fromBoolean(e.isControlDown()));
                map.set("isMetaDown", LzrNumber.fromBoolean(e.isMetaDown()));
                map.set("isShiftDown", LzrNumber.fromBoolean(e.isShiftDown()));
                map.set("modifiers", LzrNumber.of(e.getModifiers()));
                action.execute(new LzrString(type), map);
            }
        });
        return LzrNumber.ZERO;
    }

    @NotNull
    private LzrValue getLocation(@NotNull LzrValue[] args) {
        final Point location = component.getLocation();
        return LzrArray.of(LzrNumber.of(location.x), LzrNumber.of(location.y));
    }

    @NotNull
    private LzrValue getLocationOnScreen(@NotNull LzrValue[] args) {
        final Point location = component.getLocationOnScreen();
        return LzrArray.of(LzrNumber.of(location.x), LzrNumber.of(location.y));
    }

    private LzrValue setFont(LzrValue[] args) {
        component.setFont(new Font(args[0].asString(), args[1].asInt(), args[2].asInt()));
        return LzrNumber.ZERO;
    }

    private LzrValue getFont(LzrValue[] args) {
        component.getFont();
        return LzrNumber.ZERO;
    }

    private LzrValue setLocation(LzrValue[] args) {
        Arguments.check(2, args.length);
        component.setLocation(args[0].asInt(), args[1].asInt());
        return LzrNumber.ZERO;
    }



    protected static LzrFunction dimensionFunction(Supplier<Dimension> s) {
        return new LzrFunction(args -> {
            final Dimension dimension = s.get();
            return LzrArray.of(LzrNumber.of(dimension.getWidth()),LzrNumber.of(dimension.getHeight()));
        });
    }

    protected static LzrFunction voidDimensionFunction(Consumer<Dimension> s) {
        return new LzrFunction(args -> {
            Arguments.check(2, args.length);
            s.accept(new Dimension(args[0].asInt(), args[1].asInt()));
            return LzrNumber.ZERO;
        });
    }
}