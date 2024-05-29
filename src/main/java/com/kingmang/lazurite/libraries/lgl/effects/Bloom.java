package com.kingmang.lazurite.libraries.lgl.effects;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.lgl.value.EffectValue;
import com.kingmang.lazurite.runtime.LzrValue;
import javafx.scene.effect.Effect;

public class Bloom implements Function {
        @Override
        public LzrValue execute(LzrValue[] args) {
            javafx.scene.effect.Bloom effect = new javafx.scene.effect.Bloom();
            if (args.length >= 1) {
                effect.setThreshold(args[0].asNumber());
            }
            if (args.length >= 2) {
                effect.setInput((Effect)args[1].raw());
            }
            return new EffectValue(effect);
        }
    }