package com.kingmang.lazurite.libraries.lzrx.awt.lgl.effects;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.lzrx.awt.lgl.value.EffectValue;
import com.kingmang.lazurite.runtime.values.LzrValue;
import javafx.scene.effect.ColorAdjust;
import org.jetbrains.annotations.NotNull;

public class LColorAdjust implements Function {
        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            return new EffectValue(new ColorAdjust(
                    args[0].asNumber(), args[1].asNumber(), args[2].asNumber(), args[3].asNumber()));
        }
    }