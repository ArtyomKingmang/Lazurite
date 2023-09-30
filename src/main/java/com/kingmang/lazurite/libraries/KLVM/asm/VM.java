package com.kingmang.lazurite.libraries.KLVM.asm;

import com.kingmang.lazurite.runtime.Value;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VM {
    public static final int TRUE = 1;
    public static final int FALSE = 0;

    public static final int STACK_SIZE = 100;

    public int[] code;
    public int[] stack;
    private int[] globals;
    private Set<Integer> usedGlobalIndexes = new HashSet<Integer>();

    private int ip;
    private int sp;
    private int fp;

    public boolean trace = false;
    private int lineNum = 0;

    public VM(int[] codee, int ipmain, int datasize) {
        this.code = codee;
        this.stack = new int[STACK_SIZE];
        this.globals = new int[datasize];

        this.ip = ipmain;
        this.sp = -1;
        this.fp = 0;
    }



    public void exec() throws Exception {
        loop:
        while (ip < code.length) {
            if (trace) disassemble();
            switch (Bytecode.values()[nextCode()]) {

                case CALL:
                    call(nextCode(), nextCode());
                case RET:
                    ret();
                    break;
                case HALT:
                    break loop;
                case CONST:
                    pushStack(nextCode());
                    break;
                case DIV:
                    pushStack(popStack() / popStack());
                case ADD:
                    pushStack(popStack() + popStack());
                    break;
                case PRINT:
                    System.out.println(popStack());
                    break;
                case LOAD:
                    pushStack(getStack(nextCode()));
                    break;
                case LT:
                    pushStack(popStack() > popStack() ? TRUE : FALSE);
                    break;
                case JUMP:
                    ip = currCode();
                    break;
                case JUMPT:
                    jumpIf(1);
                    break;
                case JUMPF:
                    jumpIf(0);
                    break;
                case STORE:
                    setStack(nextCode(), popStack());
                    break;
                case SUB:
                    int last = popStack();
                    int first = popStack();
                    pushStack(first - last);
                    break;
                case MUL:
                    pushStack(popStack() * popStack());
                    break;

            }
        }

        if (trace && ip < code.length) disassemble();
        if (trace && !usedGlobalIndexes.isEmpty()) System.err.println(globalsToString());
    }
    private void setStack(int offset, int value) throws IndexOutOfBoundsException {
        int index = fp + offset;
        if (index < 0 || index > sp) throw new IndexOutOfBoundsException(Integer.toString(index));
        stack[index] = value;
    }

    private void call(int addr, int nargs) {
        pushStack(nargs);
        pushStack(fp);
        pushStack(ip);
        fp = sp;
        ip = addr;
    }
    private void jumpIf(int bool) {
        int addr = nextCode();
        if (popStack() == bool) ip = addr;
    }

    private int nextCode() {
        return code[ip++];
    }

    private int currCode() {
        return code[ip];
    }

    private void ret() {
        int result = popStack();
        sp = fp;
        ip = popStack();
        fp = popStack();
        int nargs = popStack();
        for (int i = 0; i < nargs; i++) {
            popStack();
        }

        pushStack(result);
    }
    private void pushStack(int value) {
        if (sp >= stack.length) {
            throw new RuntimeException("Stack overflow");
        }
        stack[++sp] = value;
    }
    private int getStack(int offset) throws IndexOutOfBoundsException {
        int index = fp + offset;
        if (index < 0 || index > sp) throw new IndexOutOfBoundsException(Integer.toString(index));
        return stack[index];
    }

    private int popStack() {
        if (sp < 0) {
            throw new RuntimeException("Stack underflow");
        }
        return stack[sp--];
    }

    private void disassemble() {
        Bytecode instr = Bytecode.values()[currCode()];
        StringBuilder buf = new StringBuilder(instr.getName());
        for (int i = 0; i < instr.getNargs(); i++) {
            buf.append(", ").append(code[ip + i + 1]);
        }

        System.err.printf("%4d: %04d  %-20s %s\n", lineNum++, ip, buf.toString(), stackToString());
    }

    private String stackToString() {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i <= sp; i++) {
            list.add(stack[i]);
        }

        return "stack: " + list;
    }

    private String globalsToString() {
        List<String> list = new ArrayList<String>();
        for (Integer index : usedGlobalIndexes) {
            list.add(index.toString() + " => " + globals[index]);
        }

        return "Global: " + list;
    }





}