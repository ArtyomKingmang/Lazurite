package com.kingmang.lazurite.libraries.lzrx.lgl.effects;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.lzrx.lgl.value.EffectValue;
import com.kingmang.lazurite.runtime.values.LzrValue;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

public class LInnerShadow implements Function {
        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            InnerShadow effect = switch (args.length) {
                case 2 -> new InnerShadow(args[0].asNumber(), (Color) args[1].raw());
                case 4 -> new InnerShadow(args[0].asNumber(), args[1].asInt(), args[2].asInt(),
                        (Color) args[3].raw());
                case 6 -> new InnerShadow(BlurType.values()[args[0].asInt()], (Color) args[1].raw(),
                        args[2].asNumber(), args[3].asNumber(), args[4].asNumber(), args[5].asNumber());
                default -> new InnerShadow();
            };
            return new EffectValue(effect);
        }
    }