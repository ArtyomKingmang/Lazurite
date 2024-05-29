package com.kingmang.lazurite.libraries.lgl.effects;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.lgl.value.EffectValue;
import com.kingmang.lazurite.runtime.LzrValue;

public class Reflection implements Function {
        @Override
        public LzrValue execute(LzrValue[] args) {
            return new EffectValue(new javafx.scene.effect.Reflection(
                    args[0].asNumber(), args[1].asNumber(), args[2].asNumber(), args[3].asNumber()));
        }
    }