package com.kingmang.lazurite.libraries.lgl.image;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.lgl.value.ImageValue;
import com.kingmang.lazurite.runtime.LzrValue;
import com.kingmang.lazurite.runtime.Types.LzrArray;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.image.WritablePixelFormat;

import java.nio.IntBuffer;

public class LoadImage implements Function {

        @Override
        public LzrValue execute(LzrValue[] args) {
            Arguments.checkAtLeast(1, args.length);
            final javafx.scene.image.Image result = switch (args.length) {
                // createImage(url)
                case 1 -> new javafx.scene.image.Image(args[0].asString());
                // createImage(width, height)
                default -> new WritableImage(args[0].asInt(), args[1].asInt());
                // createImage(w, h, pixels)
                case 3 -> {
                    final int w = args[0].asInt();
                    final int h = args[1].asInt();
                    final int size = w * h;
                    final WritableImage writableImage = new WritableImage(w, h);
                    final PixelWriter pw = writableImage.getPixelWriter();
                    final WritablePixelFormat<IntBuffer> format = WritablePixelFormat.getIntArgbInstance();
                    final int[] buffer = new int[size];
                    final LzrArray array = (LzrArray) args[2];
                    for (int i = 0; i < size; i++) {
                        buffer[i] = array.get(i).asInt();
                    }
                    pw.setPixels(0, 0, w, h, format, buffer, 0, w);
                    yield writableImage;
                }
            };
            return new ImageValue(result);
        }
    }