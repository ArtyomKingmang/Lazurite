package com.kingmang.lazurite.libraries.lzrx.awt.lgl.color;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.lzrx.awt.lgl.value.ColorValue;
import com.kingmang.lazurite.runtime.values.LzrValue;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

public class Web implements Function {

        @Override
        public @NotNull LzrValue execute(@NotNull LzrValue... args) {
            return new ColorValue(Color.web(args[0].asString(),
                    (args.length >= 2) ? args[1].asNumber() : 1d ));
        }
    }