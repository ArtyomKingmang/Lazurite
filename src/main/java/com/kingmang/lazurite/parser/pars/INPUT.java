package com.kingmang.lazurite.parser.pars;

import com.kingmang.lazurite.base.Function;
import com.kingmang.lazurite.runtime.LZR.LZRString;
import com.kingmang.lazurite.runtime.Value;

import java.util.Scanner;

public final class INPUT implements Function {

    @Override
    public Value execute(Value... args) {
        Scanner sc = new Scanner(System.in);
            return new LZRString(sc.nextLine());
    }

}
