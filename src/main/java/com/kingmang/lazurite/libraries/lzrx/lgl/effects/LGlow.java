package com.kingmang.lazurite.libraries.lzrx.lgl.effects;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.lzrx.lgl.value.EffectValue;
import com.kingmang.lazurite.runtime.values.LzrValue;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;

public class LGlow implements Function {
        @Override
        public LzrValue execute(LzrValue[] args) {
            Glow effect = new Glow();
            if (args.length >= 1) {
                effect.setLevel(args[0].asNumber());
            }
            if (args.length >= 2) {
                effect.setInput((Effect)args[1].raw());
            }
            return new EffectValue(effect);
        }
    }