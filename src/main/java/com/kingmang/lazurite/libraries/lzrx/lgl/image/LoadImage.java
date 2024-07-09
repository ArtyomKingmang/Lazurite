package com.kingmang.lazurite.libraries.lzrx.lgl.image;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.lzrx.lgl.value.ImageValue;
import com.kingmang.lazurite.runtime.values.LzrArray;
import com.kingmang.lazurite.runtime.values.LzrValue;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.image.WritablePixelFormat;

import java.nio.IntBuffer;

public class LoadImage implements Function {

        @Override
        public LzrValue execute(LzrValue[] args) {
            Arguments.checkAtLeast(1, args.length);
            final Image result = switch (args.length) {
                case 1 -> new Image(args[0].asString());
                default -> new WritableImage(args[0].asInt(), args[1].asInt());
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