package com.kingmang.lazurite.libraries.lgl.effects;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.lgl.value.EffectValue;
import com.kingmang.lazurite.runtime.LzrValue;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.Shadow;
import javafx.scene.paint.Color;

public class LShadow implements Function {
        @Override
        public LzrValue execute(LzrValue[] args) {
            Shadow effect = switch (args.length) {
                case 2 -> new Shadow(args[0].asNumber(), (Color) args[1].raw());
                case 3 -> new Shadow(BlurType.values()[args[0].asInt()], (Color) args[1].raw(),
                        args[2].asNumber());
                default -> new Shadow();
            };
            return new EffectValue(effect);
        }
    }