package com.kingmang.lazurite.libraries.lgl.effects;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.lgl.value.EffectValue;
import com.kingmang.lazurite.runtime.values.LzrValue;
import javafx.scene.effect.PerspectiveTransform;

public class LPerspectiveTransform implements Function {
        @Override
        public LzrValue execute(LzrValue[] args) {
            return new EffectValue(new PerspectiveTransform(
                    args[0].asNumber(), args[1].asNumber(), args[2].asNumber(), args[3].asNumber(),
                    args[4].asNumber(), args[5].asNumber(), args[6].asNumber(), args[7].asNumber() ));
        }
    }