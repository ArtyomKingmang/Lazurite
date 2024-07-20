package com.kingmang.lazurite.libraries.lzrx.awt.lgl.effects;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.lzrx.awt.lgl.value.EffectValue;
import com.kingmang.lazurite.runtime.values.LzrValue;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.Effect;
import org.jetbrains.annotations.NotNull;

public class LBlend implements Function {
        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            javafx.scene.effect.Blend effect = new javafx.scene.effect.Blend();
            if (args.length >= 1) {
                effect.setMode(BlendMode.values()[args[0].asInt()]);
            }
            if (args.length >= 3) {
                effect.setBottomInput((Effect)args[1].raw());
                effect.setTopInput((Effect)args[2].raw());
            }
            if (args.length >= 4) {
                effect.setOpacity(args[3].asNumber());
            }
            return new EffectValue(effect);
        }
    }