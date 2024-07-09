package com.kingmang.lazurite.libraries.lzr.lang.Character;

import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.runtime.values.LzrMap;

public class Character implements Library {
    @Override
    public void init() {
        final LzrMap characterFunctions = new LzrMap(4);
        characterFunctions.set("isAlphabetic", CharacterClass::_isAlphabetic);
        characterFunctions.set("isDigit", CharacterClass::_isDigit);
        characterFunctions.set("isLetter", CharacterClass::_isLetter);
        characterFunctions.set("isLetterOrDigit", CharacterClass::_isLetterOrDigit);
        Variables.define("Character", characterFunctions);
    }
}
