package com.kingmang.lazurite.libraries.lgl.effects;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.lgl.value.EffectValue;
import com.kingmang.lazurite.runtime.values.LzrValue;
import javafx.scene.effect.ColorInput;
import javafx.scene.paint.Color;

public class LColorInput implements Function {
        @Override
        public LzrValue execute(LzrValue[] args) {
            return new EffectValue(new ColorInput(
                    args[0].asNumber(), args[1].asNumber(), args[2].asNumber(), args[3].asNumber(), (Color) args[4].raw()));
        }
    }