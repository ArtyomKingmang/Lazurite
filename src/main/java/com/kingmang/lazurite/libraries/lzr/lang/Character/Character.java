package com.kingmang.lazurite.libraries.lzr.lang.Character;

import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.Variables;
import com.kingmang.lazurite.runtime.values.LzrMap;

public class Character implements Library {
    @Override
    public void init() {
        final LzrMap characterFunctions = new LzrMap(4);
        characterFunctions.set("isAlphabetic", CharacterClass::isAlphabeticc);
        characterFunctions.set("isDigit", CharacterClass::isDigitt);
        characterFunctions.set("isLetter", CharacterClass::isLetterr);
        characterFunctions.set("isLetterOrDigit", CharacterClass::isLetterOrDigitt);
        Variables.define("Character", characterFunctions);
    }
}
