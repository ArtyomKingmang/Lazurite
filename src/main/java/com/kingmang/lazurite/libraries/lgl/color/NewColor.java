package com.kingmang.lazurite.libraries.lgl.color;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.lgl.value.ColorValue;
import com.kingmang.lazurite.runtime.LzrValue;
import javafx.scene.paint.Color;

public class NewColor implements Function {

        @Override
        public LzrValue execute(LzrValue[] args) {
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