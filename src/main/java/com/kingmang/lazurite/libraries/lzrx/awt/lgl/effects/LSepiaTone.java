package com.kingmang.lazurite.libraries.lzrx.awt.lgl.effects;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.lzrx.awt.lgl.value.EffectValue;
import com.kingmang.lazurite.runtime.values.LzrValue;
import javafx.scene.effect.Effect;
import javafx.scene.effect.SepiaTone;
import org.jetbrains.annotations.NotNull;

public class LSepiaTone implements Function {
        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            SepiaTone effect = new SepiaTone();
            if (args.length >= 1) {
                effect.setLevel(args[0].asNumber());
            }
            if (args.length >= 2) {
                effect.setInput((Effect)args[1].raw());
            }
            return new EffectValue(effect);
        }
    }