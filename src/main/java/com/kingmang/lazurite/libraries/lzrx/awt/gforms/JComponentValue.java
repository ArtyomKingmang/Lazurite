package com.kingmang.lazurite.libraries.lzrx.awt.gforms;

import com.kingmang.lazurite.core.Converters;

import javax.swing.*;

public abstract class JComponentValue extends ContainerValue {

    final JComponent jComponent;

    public JComponentValue(int functionsCount, JComponent jComponent) {
        super(functionsCount + 14, jComponent);
        this.jComponent = jComponent;
        init();
    }

    private void init() {
        set("getAutoscrolls", Converters.voidToBoolean(jComponent::getAutoscrolls));
        set("setAutoscrolls", Converters.booleanToVoid(jComponent::setAutoscrolls));
        set("isDoubleBuffered", Converters.voidToBoolean(jComponent::isDoubleBuffered));
        set("setDoubleBuffered", Converters.booleanToVoid(jComponent::setDoubleBuffered));
        set("getInheritsPopupMenu", Converters.voidToBoolean(jComponent::getInheritsPopupMenu));
        set("setInheritsPopupMenu", Converters.booleanToVoid(jComponent::setInheritsPopupMenu));
        set("isOpaque", Converters.voidToBoolean(jComponent::isOpaque));
        set("setOpaque", Converters.booleanToVoid(jComponent::setOpaque));
        set("isRequestFocusEnabled", Converters.voidToBoolean(jComponent::isRequestFocusEnabled));
        set("setRequestFocusEnabled", Converters.booleanToVoid(jComponent::setRequestFocusEnabled));
        set("getVerifyInputWhenFocusTarget", Converters.voidToBoolean(jComponent::getVerifyInputWhenFocusTarget));
        set("setVerifyInputWhenFocusTarget", Converters.booleanToVoid(jComponent::setVerifyInputWhenFocusTarget));
        set("getToolTipText", Converters.voidToString(jComponent::getToolTipText));
        set("setToolTipText", Converters.stringToVoid(jComponent::setToolTipText));
    }
}
