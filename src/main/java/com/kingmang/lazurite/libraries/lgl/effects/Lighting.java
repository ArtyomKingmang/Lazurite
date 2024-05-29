package com.kingmang.lazurite.libraries.lgl.effects;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.lgl.value.EffectValue;
import com.kingmang.lazurite.runtime.LzrValue;
import com.kingmang.lazurite.runtime.Types.LzrArray;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Light;
import javafx.scene.paint.Color;

public class Lighting implements Function {
        @Override
        public LzrValue execute(LzrValue[] args) {
            Light light;
            final LzrArray l = (LzrArray) args[0];
            light = switch (l.size()) {
                case 3 -> new Light.Distant(l.get(0).asNumber(), l.get(1).asNumber(),
                        (Color) l.get(2).raw());
                case 4 -> new Light.Point(l.get(0).asNumber(), l.get(1).asNumber(), l.get(2).asNumber(),
                        (Color) l.get(3).raw());
                case 5 -> new Light.Spot(l.get(0).asNumber(), l.get(1).asNumber(), l.get(2).asNumber(),
                        l.get(3).asNumber(), (Color) l.get(4).raw());
                default -> null;
            };
            javafx.scene.effect.Lighting effect = new javafx.scene.effect.Lighting(light);
            if (args.length >= 2) {
                effect.setSurfaceScale(args[1].asNumber());
            }
            if (args.length >= 3) {
                effect.setDiffuseConstant(args[2].asNumber());
            }
            if (args.length >= 5) {
                effect.setSpecularConstant(args[3].asNumber());
                effect.setSpecularExponent(args[4].asNumber());
            }
            if (args.length >= 7) {
                effect.setBumpInput((Effect) args[5].raw());
                effect.setContentInput((Effect) args[6].raw());
            }
            return new EffectValue(effect);
        }
    }