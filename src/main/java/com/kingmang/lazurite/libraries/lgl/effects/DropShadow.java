package com.kingmang.lazurite.libraries.lgl.effects;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.lgl.value.EffectValue;
import com.kingmang.lazurite.runtime.LzrValue;
import javafx.scene.effect.BlurType;
import javafx.scene.paint.Color;

public class DropShadow implements Function {
        @Override
        public LzrValue execute(LzrValue[] args) {
            javafx.scene.effect.DropShadow effect = switch (args.length) {
                case 2 -> new javafx.scene.effect.DropShadow(args[0].asNumber(), (Color) args[1].raw());
                case 4 -> new javafx.scene.effect.DropShadow(args[0].asNumber(), args[1].asInt(), args[2].asInt(),
                        (Color) args[3].raw());
                case 6 -> new javafx.scene.effect.DropShadow(BlurType.values()[args[0].asInt()], (Color) args[1].raw(),
                        args[2].asNumber(), args[3].asNumber(), args[4].asNumber(), args[5].asNumber());
                default -> new javafx.scene.effect.DropShadow();
            };
            return new EffectValue(effect);
        }
    }