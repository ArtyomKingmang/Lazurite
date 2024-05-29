package com.kingmang.lazurite.libraries.lgl.value;

import com.kingmang.lazurite.runtime.LzrValue;
import com.kingmang.lazurite.runtime.Types.LzrArray;
import com.kingmang.lazurite.runtime.Types.LzrMap;
import com.kingmang.lazurite.runtime.Types.LzrNumber;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritablePixelFormat;

import java.nio.IntBuffer;

public class ImageValue extends LzrMap {

        public final Image image;

        public ImageValue(Image image) {
            super(8);
            this.image = image;
            init();
        }

        private void init() {
            set("width", LzrNumber.of(image.getWidth()));
            set("height", LzrNumber.of(image.getHeight()));
            set("preserveRatio", LzrNumber.fromBoolean(image.isPreserveRatio()));
            set("smooth", LzrNumber.fromBoolean(image.isSmooth()));
            set("getPixels", this::getPixels);
        }

        private LzrValue getPixels(LzrValue[] args) {
            final int w = (int) image.getWidth();
            final int h = (int) image.getHeight();
            final int size = w * h;
            final PixelReader pr = image.getPixelReader();
            final WritablePixelFormat<IntBuffer> format = WritablePixelFormat.getIntArgbInstance();
            final int[] buffer = new int[size];
            pr.getPixels(0, 0, w, h, format, buffer, 0, w);

            final LzrArray result = new LzrArray(size);
            for (int i = 0; i < size; i++) {
                result.set(i, LzrNumber.of(buffer[i]));
            }
            return result;
        }

        @Override
        public String toString() {
            return "Image " + image;
        }
    }