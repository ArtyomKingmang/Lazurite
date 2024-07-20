package com.kingmang.lazurite.libraries.lzrx.awt.lgl.effects;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.lzrx.awt.lgl.value.EffectValue;
import com.kingmang.lazurite.runtime.values.LzrValue;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

public class LDropShadow implements Function {
        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            DropShadow effect = switch (args.length) {
                case 2 -> new DropShadow(args[0].asNumber(), (Color) args[1].raw());
                case 4 -> new DropShadow(args[0].asNumber(), args[1].asInt(), args[2].asInt(),
                        (Color) args[3].raw());
                case 6 -> new DropShadow(BlurType.values()[args[0].asInt()], (Color) args[1].raw(),
                        args[2].asNumber(), args[3].asNumber(), args[4].asNumber(), args[5].asNumber());
                default -> new DropShadow();
            };
            return new EffectValue(effect);
        }
    }