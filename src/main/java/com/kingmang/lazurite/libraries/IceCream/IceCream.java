package com.kingmang.lazurite.libraries.IceCream;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.core.KEYWORD;
import com.kingmang.lazurite.libraries.IceCream.core.Ice;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
import com.kingmang.lazurite.runtime.Value;

public class IceCream implements Library {
    static Ice ice = new Ice();
    @Override
    public void init(){
        KEYWORD.put("ic", new ic());
        KEYWORD.put("setPrefix", new setPrefix());

    }

    private static class ic implements Function {
        @Override
        public Value execute(Value... args) {
            ice.ic(args[0]);
            return LZRNumber.ZERO;
        }
    }
    private static class setPrefix implements Function {
        @Override
        public Value execute(Value... args) {
            ice.setPrefix(args[0].asString());
            return LZRNumber.ZERO;
        }
    }


}
