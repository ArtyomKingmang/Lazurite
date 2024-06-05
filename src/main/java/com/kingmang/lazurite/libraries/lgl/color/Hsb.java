package com.kingmang.lazurite.libraries.lgl.color;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.lgl.value.ColorValue;
import com.kingmang.lazurite.runtime.values.LzrValue;
import javafx.scene.paint.Color;

public class Hsb implements Function {

        @Override
        public LzrValue execute(LzrValue[] args) {
            double h = args[0].asNumber();
            double s = args[1].asNumber();
            double b = args[2].asNumber();
            double opacity = (args.length >= 4) ? args[3].asNumber() : 1d;
            return new ColorValue(Color.hsb(h, s, b, opacity));
        }
    }