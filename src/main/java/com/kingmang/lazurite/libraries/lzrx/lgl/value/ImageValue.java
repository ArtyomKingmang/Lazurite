package com.kingmang.lazurite.libraries.lzrx.lgl.value;

import com.kingmang.lazurite.runtime.values.LzrArray;
import com.kingmang.lazurite.runtime.values.LzrMap;
import com.kingmang.lazurite.runtime.values.LzrNumber;
import com.kingmang.lazurite.runtime.values.LzrValue;
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

        return new LzrArray(size, index -> LzrNumber.of(buffer[index]));
    }

    @Override
    public String toString() {
        return "Image " + image;
    }
}