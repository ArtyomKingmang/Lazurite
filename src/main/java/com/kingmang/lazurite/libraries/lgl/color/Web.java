package com.kingmang.lazurite.libraries.lgl.color;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.lgl.value.ColorValue;
import com.kingmang.lazurite.runtime.LzrValue;
import javafx.scene.paint.Color;

public class Web implements Function {

        @Override
        public LzrValue execute(LzrValue[] args) {
            return new ColorValue(Color.web(args[0].asString(),
                    (args.length >= 2) ? args[1].asNumber() : 1d ));
        }
    }