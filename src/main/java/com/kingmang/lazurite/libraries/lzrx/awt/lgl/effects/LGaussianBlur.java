package com.kingmang.lazurite.libraries.lzrx.awt.lgl.effects;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.lzrx.awt.lgl.value.EffectValue;
import com.kingmang.lazurite.runtime.values.LzrValue;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import org.jetbrains.annotations.NotNull;

public class LGaussianBlur implements Function {
        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            GaussianBlur effect = new GaussianBlur();
            if (args.length >= 1) {
                effect.setRadius(args[0].asNumber());
            }
            if (args.length >= 2) {
                effect.setInput((Effect)args[1].raw());
            }
            return new EffectValue(effect);
        }
    }