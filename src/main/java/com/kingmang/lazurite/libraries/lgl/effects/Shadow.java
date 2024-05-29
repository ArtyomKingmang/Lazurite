package com.kingmang.lazurite.libraries.lgl.effects;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.lgl.value.EffectValue;
import com.kingmang.lazurite.runtime.LzrValue;
import javafx.scene.effect.BlurType;
import javafx.scene.paint.Color;

public class Shadow implements Function {
        @Override
        public LzrValue execute(LzrValue[] args) {
            javafx.scene.effect.Shadow effect = switch (args.length) {
                case 2 -> new javafx.scene.effect.Shadow(args[0].asNumber(), (Color) args[1].raw());
                case 3 -> new javafx.scene.effect.Shadow(BlurType.values()[args[0].asInt()], (Color) args[1].raw(),
                        args[2].asNumber());
                default -> new javafx.scene.effect.Shadow();
            };
            return new EffectValue(effect);
        }
    }