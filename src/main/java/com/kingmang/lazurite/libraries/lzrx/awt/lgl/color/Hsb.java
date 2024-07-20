package com.kingmang.lazurite.libraries.lzrx.awt.lgl.color;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.lzrx.awt.lgl.value.ColorValue;
import com.kingmang.lazurite.runtime.values.LzrValue;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

public class Hsb implements Function {

        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            double h = args[0].asNumber();
            double s = args[1].asNumber();
            double b = args[2].asNumber();
            double opacity = (args.length >= 4) ? args[3].asNumber() : 1d;
            return new ColorValue(Color.hsb(h, s, b, opacity));
        }
    }