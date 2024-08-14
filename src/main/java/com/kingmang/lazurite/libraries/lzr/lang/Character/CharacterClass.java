package com.kingmang.lazurite.libraries.lzr.lang.Character;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.runtime.values.LzrNumber;
import com.kingmang.lazurite.runtime.values.LzrValue;

public final class CharacterClass {

        public static LzrValue _isAlphabetic(LzrValue[] args) {
            Arguments.check(1, args.length);
            char current = (char) args[0].asInt();
            boolean result = java.lang.Character.isAlphabetic(current);
            if (result)
                return LzrNumber.ONE;
            else
                return LzrNumber.ZERO;
        }

        public static LzrValue _isDigit(LzrValue[] args) {
            Arguments.check(1, args.length);
            char current = (char) args[0].asInt();
            boolean result = java.lang.Character.isDigit(current);
            if (result)
                return LzrNumber.ONE;
            else
                return LzrNumber.ZERO;
        }

        public static LzrValue _isLetterOrDigit(LzrValue[] args) {
            Arguments.check(1, args.length);
            char current = (char) args[0].asInt();
            boolean result = java.lang.Character.isLetterOrDigit(current);
            if (result)
                return LzrNumber.ONE;
            else
                return LzrNumber.ZERO;
        }

        public static LzrValue _isLetter(LzrValue[] args) {
            Arguments.check(1, args.length);
            char current = (char) args[0].asInt();
            boolean result = java.lang.Character.isLetter(current);
            if (result)
                return LzrNumber.ONE;
            else
                return LzrNumber.ZERO;
        }




    }