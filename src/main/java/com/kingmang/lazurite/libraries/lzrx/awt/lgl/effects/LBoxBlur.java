package com.kingmang.lazurite.libraries.lzrx.awt.lgl.effects;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.lzrx.awt.lgl.value.EffectValue;
import com.kingmang.lazurite.runtime.values.LzrValue;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import org.jetbrains.annotations.NotNull;

public class LBoxBlur implements Function {
        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            BoxBlur effect = new BoxBlur();
            if (args.length >= 3) {
                effect.setWidth(args[0].asNumber());
                effect.setHeight(args[1].asNumber());
                effect.setIterations(args[2].asInt());
            }
            if (args.length >= 4) {
                effect.setInput((Effect)args[3].raw());
            }
            return new EffectValue(effect);
        }
    }