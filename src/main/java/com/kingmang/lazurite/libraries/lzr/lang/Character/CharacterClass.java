package com.kingmang.lazurite.libraries.lzr.lang.Character;

import com.kingmang.lazurite.core.Arguments;
import com.kingmang.lazurite.runtime.values.LzrNumber;
import com.kingmang.lazurite.runtime.values.LzrValue;

public final class CharacterClass {

        public static LzrValue isAlphabeticc(LzrValue[] args) {
            Arguments.check(1, args.length);
            char current = (char) args[0].asInt();
            boolean result = java.lang.Character.isAlphabetic(current);
            if(result)
                return LzrNumber.ZERO;
            else
                return LzrNumber.MINUS_ONE;
        }

        public static LzrValue isDigitt(LzrValue[] args) {
            Arguments.check(1, args.length);
            char current = (char) args[0].asInt();
            boolean result = java.lang.Character.isDigit(current);
            if(result)
                return LzrNumber.ZERO;
            else
                return LzrNumber.MINUS_ONE;
        }

        public static LzrValue isLetterOrDigitt(LzrValue[] args) {
            Arguments.check(1, args.length);
            char current = (char) args[0].asInt();
            boolean result = java.lang.Character.isLetterOrDigit(current);
            if(result)
                return LzrNumber.ZERO;
            else
                return LzrNumber.MINUS_ONE;
        }

        public static LzrValue isLetterr(LzrValue[] args) {
            Arguments.check(1, args.length);
            char current = (char) args[0].asInt();
            boolean result = java.lang.Character.isLetter(current);
            if(result)
                return LzrNumber.ZERO;
            else
                return LzrNumber.MINUS_ONE;
        }




    }