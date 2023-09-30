package com.kingmang.lazurite.libraries.KLVM;

/*
import com.kingmang.lazurite.core.Function;
import com.kingmang.lazurite.core.KEYWORD;
import com.kingmang.lazurite.libraries.KLVM.asm.VM;
import com.kingmang.lazurite.libraries.Module;
import com.kingmang.lazurite.runtime.LZR.LZRNumber;
import com.kingmang.lazurite.runtime.Value;
*/
public class vm {


   /* public void init() {
        KEYWORD.put("runVM", new vm.runVm());
    }


    private class runVm implements Function {
        @Override
        public Value execute(Value... args) {
            Value[] instr = args[0].asArray().getValues();
            short[] code = new short[instr.length];
            for (int i = 0; i < instr.length; i++) {
                String op = instr[i].asArray().getValues()[0].asString().getValue();
                int arg = instr[i].asArray().getValues()[1].asNumber().getValue();
                switch (op) {
                    case "LOAD_CONST":
                        code[i] = CONST;
                        code[++i] = (short) arg;
                        break;
                    case "PRINT":
                        code[i] = PRINT;
                        break;
                }
            }
            VM vm = new VM(code, 0, 0);
            try {
                vm.trace = true;
                vm.exec();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return LZRNumber.ZERO;
        }
    }
*/

}
