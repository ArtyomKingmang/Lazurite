package com.kingmang.lazurite.libraries.lzrx.lgl.value;

import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.runtime.values.LzrValue;
import javafx.scene.effect.Effect;
import org.jetbrains.annotations.NotNull;

public class EffectValue implements LzrValue {
        public static final int FX_EFFECT_TYPE = 5301;
        private final Effect effect;
        
        public EffectValue(Effect effect) {
            this.effect = effect;
        }
        
        @Override
        public Object raw() {
            return effect;
        }
        
        @Override
        public int asInt() {
            throw new LzrException("TypeException: ", "Cannot cast JavaFX Effect to integer");
        }
        
        @Override
        public double asNumber() {
            throw new LzrException("TypeException: ", "Cannot cast JavaFX Effect to number");
        }
        
        @Override
        public String asString() {
            return effect.toString();
        }

        @Override
        public int[] asArray() {
            return new int[0];
        }

        @Override
        public int type() {
            return FX_EFFECT_TYPE;
        }
        
        @Override
        public String toString() {
            return "Effect " + effect;
        }

        @Override
        public int compareTo(@NotNull LzrValue lzrValue) {
            return 0;
        }
    }