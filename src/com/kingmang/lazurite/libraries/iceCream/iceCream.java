package com.kingmang.lazurite.libraries.iceCream;

import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.libraries.iceCream.core.Ice;
import com.kingmang.lazurite.libraries.Library;
import com.kingmang.lazurite.runtime.LZR.LZRMap;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
import com.kingmang.lazurite.runtime.Value;
import com.kingmang.lazurite.runtime.Variables;

public class iceCream implements Library {
    static Ice ice = new Ice();
    @Override
    public void init(){
        LZRMap ice = new LZRMap(2);
        ice.set("ic", new ic());
        ice.set("setPrefix", new setPrefix());
        Variables.define("ice",ice);
    }

    private static class ic implements Function {
        @Override
        public Value execute(Value... args) {
            for(int i=0; i<args.length; i++){
                ice.ic(args[i]);
            }
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
