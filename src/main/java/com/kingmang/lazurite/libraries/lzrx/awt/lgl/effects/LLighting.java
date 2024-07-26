package com.kingmang.lazurite.libraries.lzrx.awt.lgl.effects;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.lzrx.awt.lgl.value.EffectValue;
import com.kingmang.lazurite.runtime.values.LzrArray;
import com.kingmang.lazurite.runtime.values.LzrValue;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

public class LLighting implements Function {
        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            Light light;
            final LzrArray l = (LzrArray) args[0];
            switch (l.size()) {
                case 3:
                    light = new Light.Distant(l.get(0).asNumber(), l.get(1).asNumber(),
                            (Color) l.get(2).raw());
                    break;
                case 4:
                    light = new Light.Point(l.get(0).asNumber(), l.get(1).asNumber(), l.get(2).asNumber(),
                            (Color) l.get(3).raw());
                    break;
                case 5:
                    light = new Light.Spot(l.get(0).asNumber(), l.get(1).asNumber(), l.get(2).asNumber(),
                            l.get(3).asNumber(), (Color) l.get(4).raw());
                    break;
                default:
                    light = null;
                    break;
            }
            Lighting effect = new Lighting(light);
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