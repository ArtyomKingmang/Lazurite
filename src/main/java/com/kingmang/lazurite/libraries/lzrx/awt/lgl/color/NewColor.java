package com.kingmang.lazurite.libraries.lzrx.awt.lgl.color;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.lzrx.awt.lgl.value.ColorValue;
import com.kingmang.lazurite.runtime.values.LzrValue;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

public class NewColor implements Function {

        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            double r, g, b, opacity;
            if (args.length == 1) {
                final int color = args[0].asInt();
                r = ((color >> 16) & 0xFF) / 255.0;
                g = ((color >> 8) & 0xFF) / 255.0;
                b = (color & 0xFF) / 255.0;
                opacity = ((color >> 24) & 0xFF) / 255.0;
            } else {
                r = args[0].asNumber();
                g = args[1].asNumber();
                b = args[2].asNumber();
                opacity = (args.length >= 4) ? args[3].asNumber() : 1d;
            }
            return new ColorValue(new Color(r, g, b, opacity));
        }
    }