package com.kingmang.lazurite.libraries.vm.asm;

public enum Bytecode {
    JUMPT("jumpt",1),
    JUMPF("jumpf",1),
    STORE("store",1),
    CALL("call",2),
    RET("ret",0),
    DIV("div",0),
    LOAD("load",1),
    HALT("halt", 0),
    CONST("const", 1),
    ADD("add", 0),
    SUB("sub", 0),
    MUL("mul", 0),
    PRINT("print",0),
    JUMP("jump",1),
    LT("lt",0);


    private final String name;
    private final int nargs;

    Bytecode(String name, int nargs) {
        this.name = name;
        this.nargs = nargs;
    }

    public String getName() {
        return name;
    }

    public int getNargs() {
        return nargs;
    }
}
