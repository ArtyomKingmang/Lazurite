package com.kingmang.lazurite.libraries.lzrx.awt.lgl.effects;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.lzrx.awt.lgl.value.EffectValue;
import com.kingmang.lazurite.runtime.values.LzrValue;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Effect;
import org.jetbrains.annotations.NotNull;

public class LBloom implements Function {
        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            Bloom effect = new Bloom();
            if (args.length >= 1) {
                effect.setThreshold(args[0].asNumber());
            }
            if (args.length >= 2) {
                effect.setInput((Effect)args[1].raw());
            }
            return new EffectValue(effect);
        }
    }