package com.kingmang.lazurite.libraries.lgl.color;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.lgl.value.ColorValue;
import com.kingmang.lazurite.runtime.values.LzrValue;
import javafx.scene.paint.Color;

public class Rgb implements Function {

        @Override
        public LzrValue execute(LzrValue[] args) {
            int r = args[0].asInt();
            int g = args[1].asInt();
            int b = args[2].asInt();
            double opacity = (args.length >= 4) ? args[3].asNumber() : 1d;
            return new ColorValue(Color.rgb(r, g, b, opacity));
        }
    }