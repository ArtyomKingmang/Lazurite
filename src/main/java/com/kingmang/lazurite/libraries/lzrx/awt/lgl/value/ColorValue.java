package com.kingmang.lazurite.libraries.lzrx.awt.lgl.value;

import com.kingmang.lazurite.runtime.values.LzrValue;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

public class ColorValue implements LzrValue {
        private static final int FX_COLOR_TYPE = 5302;
        private final Color color;
        
        public ColorValue(Color effect) {
            this.color = effect;
        }

        @NotNull
        @Override
        public Color raw() {
            return color;
        }
        
        @Override
        public int asInt() {
            final int a = (int) (color.getOpacity() * 255) & 0xFF;
            final int r = (int) (color.getRed() * 255) & 0xFF;
            final int g = (int) (color.getGreen() * 255) & 0xFF;
            final int b = (int) (color.getBlue() * 255) & 0xFF;
            return ((a << 24) | (r << 16) | (g << 8) | b);
        }
        
        @Override
        public double asNumber() {
            return asInt();
        }
        
        @NotNull
        @Override
        public String asString() {
            return color.toString();
        }

        @NotNull
        @Override
        public int[] asArray() {
            return new int[0];
        }

        @Override
        public int type() {
            return FX_COLOR_TYPE;
        }
        

        @Override
        public String toString() {
            return "Color " + color;
        }

        @Override
        public int compareTo(@NotNull LzrValue lzrValue) {
            return 0;
        }
    }