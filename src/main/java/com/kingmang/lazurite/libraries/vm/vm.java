package com.kingmang.lazurite.libraries.vm;


import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.core.KEYWORD;
import com.kingmang.lazurite.libraries.Module;
import com.kingmang.lazurite.libraries.vm.asm.VM;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
import com.kingmang.lazurite.runtime.Value;

import static com.kingmang.lazurite.libraries.vm.asm.Bytecode.*;

public class vm implements Module {
    public void init(){
        KEYWORD.put("printf", new vm.printf());
    }

    private static class printf implements Function{
        @Override
        public Value execute(Value... args) {
            int[] instr = {
                    CONST.ordinal(), (int) args[0].asNumber(),
                    PRINT.ordinal(),
                    HALT.ordinal()
            };
            VM vm = new VM(instr, 0,0);
            try {
                vm.exec();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return LZRNumber.ZERO;
        }
    }
}
