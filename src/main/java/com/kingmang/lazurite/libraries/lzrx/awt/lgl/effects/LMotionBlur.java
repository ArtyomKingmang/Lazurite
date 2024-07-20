package com.kingmang.lazurite.libraries.lzrx.awt.lgl.effects;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.lzrx.awt.lgl.value.EffectValue;
import com.kingmang.lazurite.runtime.values.LzrValue;
import javafx.scene.effect.Effect;
import javafx.scene.effect.MotionBlur;
import org.jetbrains.annotations.NotNull;

public class LMotionBlur implements Function {
        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            MotionBlur effect = new MotionBlur();
            if (args.length >= 2) {
                effect.setAngle(args[0].asNumber());
                effect.setRadius(args[1].asNumber());
            }
            if (args.length >= 3) {
                effect.setInput((Effect)args[2].raw());
            }
            return new EffectValue(effect);
        }
    }