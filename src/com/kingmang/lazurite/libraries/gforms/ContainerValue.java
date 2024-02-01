package com.kingmang.lazurite.libraries.gforms;


import com.kingmang.lazurite.core.*;
import com.kingmang.lazurite.runtime.Lzr.LzrFunction;
import com.kingmang.lazurite.runtime.Lzr.LzrNumber;
import com.kingmang.lazurite.runtime.Value;

import java.awt.Component;
import java.awt.Container;
import javax.swing.JLabel;

public abstract class ContainerValue extends ComponentValue {

    final Container container;

    public ContainerValue(int functionsCount, Container container) {
        super(functionsCount + 10, container);
        this.container = container;
        init();
    }

    private void init() {
        set("add", new LzrFunction(this::add));
        set("remove", new LzrFunction(this::remove));
        set("removeAll", Converters.voidToVoid(container::removeAll));
        set("getAlignmentX", Converters.voidToFloat(container::getAlignmentX));
        set("getAlignmentY", Converters.voidToFloat(container::getAlignmentY));
        set("getComponentCount", Converters.voidToInt(container::getComponentCount));
        set("isFocusCycleRoot", Converters.voidToBoolean(container::isFocusCycleRoot));
        set("isValidateRoot", Converters.voidToBoolean(container::isValidateRoot));
        set("setFocusCycleRoot", Converters.booleanToVoid(container::setFocusCycleRoot));
        set("setLayout", new LzrFunction(this::setLayout));
    }

    private Value add(Value... args) {
        Arguments.checkRange(1, 3, args.length);

        final Component newComponent;
        if (args[0] instanceof ComponentValue) {
            newComponent = ((ComponentValue) args[0]).component;
        } else {
            newComponent = new JLabel(args[0].asString());
        }
        switch (args.length) {
            case 1:
                container.add(newComponent);
                break;
            case 2:
                if (args[1].type() == Types.NUMBER) {
                    // add(component, index)
                    container.add(newComponent, args[1].asInt());
                } else {
                    // add(component, constraints)
                    container.add(newComponent, args[1].raw());
                }
                break;
            case 3:
                // add(component, constraints, index)
                container.add(newComponent, args[1].raw(), args[2].asInt());
                break;
        }
        return LzrNumber.ZERO;
    }

    private Value remove(Value... args) {
        Arguments.check(1, args.length);
        if (args[0] instanceof com.kingmang.lazurite.libraries.gforms.JComponentValue) {
            container.remove(((JComponentValue) args[0]).component);
        } else {
            container.remove(args[0].asInt());
        }
        return LzrNumber.ZERO;
    }

    private Value setLayout(Value... args) {
        Arguments.check(1, args.length);
        container.setLayout(((LayoutManagerValue) args[0]).layout);
        return LzrNumber.ZERO;
    }
}